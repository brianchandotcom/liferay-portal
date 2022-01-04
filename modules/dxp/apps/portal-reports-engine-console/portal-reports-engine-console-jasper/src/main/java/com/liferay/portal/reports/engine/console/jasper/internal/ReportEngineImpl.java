/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.reports.engine.console.jasper.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.reports.engine.ByteArrayReportResultContainer;
import com.liferay.portal.reports.engine.ReportEngine;
import com.liferay.portal.reports.engine.ReportFormatExporter;
import com.liferay.portal.reports.engine.ReportFormatExporterRegistry;
import com.liferay.portal.reports.engine.ReportGenerationException;
import com.liferay.portal.reports.engine.ReportRequest;
import com.liferay.portal.reports.engine.ReportRequestContext;
import com.liferay.portal.reports.engine.ReportResultContainer;
import com.liferay.portal.reports.engine.console.jasper.internal.compiler.ReportCompiler;
import com.liferay.portal.reports.engine.console.jasper.internal.fill.manager.ReportFillManager;
import com.liferay.portal.reports.engine.console.jasper.internal.fill.manager.ReportFillManagerRegistry;
import com.liferay.portal.reports.engine.constants.ReportsEngineDestinationNames;
import com.liferay.portal.reports.engine.messaging.ReportCompilerRequestMessageListener;
import com.liferay.portal.reports.engine.messaging.ReportRequestMessageListener;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 * @author Brian Greenwald
 */
@Component(immediate = true, service = ReportEngine.class)
public class ReportEngineImpl implements ReportEngine {

	@Override
	public void compile(ReportRequest reportRequest)
		throws ReportGenerationException {

		try {
			_reportCompiler.compile(
				reportRequest.getReportDesignRetriever(), true);
		}
		catch (Exception exception) {
			throw new ReportGenerationException(
				"Unable to compile report: " +
					StackTraceUtil.getStackTrace(exception));
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	public void execute(
			ReportRequest reportRequest, ReportResultContainer resultContainer)
		throws ReportGenerationException {

		try {
			JasperReport jasperReport = _reportCompiler.compile(
				reportRequest.getReportDesignRetriever());

			ReportRequestContext reportRequestContext =
				reportRequest.getReportRequestContext();

			ReportFillManager reportFillManager =
				_reportFillManagerRegistry.getReportFillManager(
					reportRequestContext.getReportDataSourceType());

			JasperPrint jasperPrint = reportFillManager.fillReport(
				jasperReport, reportRequest);

			ReportFormatExporter reportFormatExporter =
				_reportFormatExporterRegistry.getReportFormatExporter(
					reportRequest.getReportFormat());

			reportFormatExporter.format(
				jasperPrint, reportRequest, resultContainer);
		}
		catch (Exception exception) {
			throw new ReportGenerationException(
				"Unable to execute report: " +
					StackTraceUtil.getStackTrace(exception));
		}
	}

	@Override
	public Map<String, String> getEngineParameters() {
		return _engineParameters;
	}

	@Override
	public void init(ServletContext servletContext) {
	}

	@Override
	public void setEngineParameters(Map<String, String> engineParameters) {
		_engineParameters = engineParameters;
	}

	@Activate
	protected void activate(ComponentContext componentContext) {
		_bundleContext = componentContext.getBundleContext();

		MessageListener reportCompilerRequestMessageListener =
			new ReportCompilerRequestMessageListener(
				this, new ByteArrayReportResultContainer());

		_messageListeners.put(
			ReportsEngineDestinationNames.REPORT_COMPILER,
			reportCompilerRequestMessageListener);

		MessageListener reportRequestMessageListener =
			new ReportRequestMessageListener(
				this, new ByteArrayReportResultContainer());

		_messageListeners.put(
			ReportsEngineDestinationNames.REPORT_REQUEST,
			reportRequestMessageListener);

		for (String destinationName : _messageListeners.keySet()) {
			_registerDestination(destinationName);
		}
	}

	@Deactivate
	protected void deactivate() {
		for (ServiceRegistration<Destination> destinationServiceRegistration :
				_destinationServiceRegistrations) {

			Destination destination = _bundleContext.getService(
				destinationServiceRegistration.getReference());

			destinationServiceRegistration.unregister();

			destination.destroy();
		}

		_destinationServiceRegistrations.clear();

		for (ServiceRegistration<MessageListener>
				messageListenerServiceRegistration :
					_messageListenerServiceRegistrations) {

			messageListenerServiceRegistration.unregister();
		}

		_messageListeners.clear();

		_messageListenerServiceRegistrations.clear();

		_bundleContext = null;
	}

	@Modified
	protected void modified(ComponentContext componentContext) {
		deactivate();

		activate(componentContext);
	}

	private void _registerDestination(String destinationName) {
		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_PARALLEL,
				destinationName);

		destinationConfiguration.setMaximumQueueSize(_MAXIMUM_QUEUE_SIZE);

		RejectedExecutionHandler rejectedExecutionHandler =
			new ThreadPoolExecutor.CallerRunsPolicy() {

				@Override
				public void rejectedExecution(
					Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {

					if (_log.isWarnEnabled()) {
						_log.warn(
							"The current thread will handle the request " +
								"because the graph walker's task queue is at " +
									"its maximum capacity");
					}

					super.rejectedExecution(runnable, threadPoolExecutor);
				}

			};

		destinationConfiguration.setRejectedExecutionHandler(
			rejectedExecutionHandler);

		Destination destination = _destinationFactory.createDestination(
			destinationConfiguration);

		Dictionary<String, Object> destinationProperties =
			HashMapDictionaryBuilder.<String, Object>put(
				"destination.name", destination.getName()
			).build();

		ServiceRegistration<Destination> destinationServiceRegistration =
			_bundleContext.registerService(
				Destination.class, destination, destinationProperties);

		_destinationServiceRegistrations.add(destinationServiceRegistration);

		MessageListener messageListener = _messageListeners.get(
			destinationName);

		destination.register(messageListener);

		ServiceRegistration<MessageListener>
			messageListenerServiceRegistration = _bundleContext.registerService(
				MessageListener.class, messageListener, destinationProperties);

		_messageListenerServiceRegistrations.add(
			messageListenerServiceRegistration);
	}

	private static final int _MAXIMUM_QUEUE_SIZE = 200;

	private static final Log _log = LogFactoryUtil.getLog(
		ReportEngineImpl.class);

	private BundleContext _bundleContext;

	@Reference
	private DestinationFactory _destinationFactory;

	private final List<ServiceRegistration<Destination>>
		_destinationServiceRegistrations = new ArrayList<>();
	private Map<String, String> _engineParameters;
	private final Map<String, MessageListener> _messageListeners =
		new ConcurrentHashMap<>();
	private final List<ServiceRegistration<MessageListener>>
		_messageListenerServiceRegistrations = new ArrayList<>();

	@Reference(
		cardinality = ReferenceCardinality.MANDATORY,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile ReportCompiler _reportCompiler;

	@Reference
	private ReportFillManagerRegistry _reportFillManagerRegistry;

	@Reference
	private ReportFormatExporterRegistry _reportFormatExporterRegistry;

}