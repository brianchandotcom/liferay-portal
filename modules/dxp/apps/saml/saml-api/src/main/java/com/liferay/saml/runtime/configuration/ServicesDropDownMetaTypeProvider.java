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

package com.liferay.saml.runtime.configuration;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.saml.runtime.credential.KeyStoreManager;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.metatype.AttributeDefinition;
import org.osgi.service.metatype.MetaTypeProvider;
import org.osgi.service.metatype.ObjectClassDefinition;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Carlos Sierra Andrés
 */
public class ServicesDropDownMetaTypeProvider
	implements MetaTypeProvider, Closeable {

	public ServicesDropDownMetaTypeProvider(
		BundleContext bundleContext, String className, String metatypePID,
		String attributeID) {

		this(
			bundleContext, className, metatypePID, null, null, attributeID,
			null, null);
	}

	public ServicesDropDownMetaTypeProvider(
		BundleContext bundleContext, String className, String metatypePID,
		String metatypeName, String metatypeDescription, String attributeID,
		String attributeName, String attributeDescription) {

		this(
			bundleContext, className, metatypePID, metatypeName,
			metatypeDescription, attributeID, attributeName,
			attributeDescription, s -> s.getProperty("component.name"),
			s -> "(component.name=" + s.getProperty("component.name") + ")");
	}

	public ServicesDropDownMetaTypeProvider(
		BundleContext bundleContext, String className, String metatypePID,
		String metatypeName, String metatypeDescription, String attributeID,
		String attributeName, String attributeDescription,
		Function<ServiceReference<?>, Object> labelFunction,
		Function<ServiceReference<?>, String> valuesFunction) {

		_metatypePID = metatypePID;
		_metatypeName = metatypeName;
		_metatypeDescription = metatypeDescription;
		_attributeID = attributeID;
		_attributeName = attributeName;
		_attributeDescription = attributeDescription;
		_labelFunction = labelFunction;
		_valuesFunction = valuesFunction;

		try {
			Filter filter = bundleContext.createFilter(
				String.format(
					"(&(objectClass=%s)(%s))", className, "component.name=*"));

			_serviceTracker = new ServiceTracker<>(bundleContext, filter, null);

			_serviceTracker.open();
		}
		catch (InvalidSyntaxException ise) {
			throw new IllegalArgumentException(
				className + " is an invalid class name", ise);
		}
	}

	@Override
	public void close() throws IOException {
		if (_serviceTracker != null) {
			_serviceTracker.close();
		}
	}

	@Override
	public String[] getLocales() {
		Set<Locale> availableLocales = LanguageUtil.getAvailableLocales();

		Stream<Locale> stream = availableLocales.stream();

		return stream.map(
			Locale::toLanguageTag
		).toArray(
			String[]::new
		);
	}

	public String getMetatypePID() {
		return _metatypePID;
	}

	@Override
	public ObjectClassDefinition getObjectClassDefinition(
		String id, String locale) {

		return new ObjectClassDefinition() {

			@Override
			public AttributeDefinition[] getAttributeDefinitions(int filter) {
				if ((filter == OPTIONAL) || (filter == ALL)) {
					return new AttributeDefinition[] {
						new AttributeDefinition() {

							@Override
							public int getCardinality() {
								return 0;
							}

							@Override
							public String[] getDefaultValue() {
								return new String[0];
							}

							@Override
							public String getDescription() {
								return _attributeDescription;
							}

							@Override
							public String getID() {
								return _attributeID;
							}

							@Override
							public String getName() {
								return _attributeName;
							}

							@Override
							public String[] getOptionLabels() {
								Stream<ServiceReference<?>> stream =
									_getServiceReferences();

								return stream.map(
									_labelFunction
								).toArray(
									String[]::new
								);
							}

							@Override
							public String[] getOptionValues() {
								Stream<ServiceReference<?>> stream =
									_getServiceReferences();

								return stream.map(
									_valuesFunction
								).toArray(
									String[]::new
								);
							}

							@Override
							public int getType() {
								return STRING;
							}

							@Override
							public String validate(String value) {
								return null;
							}

						}
					};
				}
				else {
					return new AttributeDefinition[0];
				}
			}

			@Override
			public String getDescription() {
				return _metatypeDescription;
			}

			@Override
			public InputStream getIcon(int size) throws IOException {
				return null;
			}

			@Override
			public String getID() {
				return _metatypePID;
			}

			@Override
			public String getName() {
				return _metatypeName;
			}

		};
	}

	private Stream<ServiceReference<?>> _getServiceReferences() {
		ServiceReference<KeyStoreManager>[] serviceReferences =
			_serviceTracker.getServiceReferences();

		if (serviceReferences == null) {
			return Stream.empty();
		}

		return Arrays.stream(serviceReferences);
	}

	private String _attributeDescription;
	private String _attributeID;
	private String _attributeName;
	private Function<ServiceReference<?>, Object> _labelFunction;
	private String _metatypeDescription;
	private String _metatypeName;
	private String _metatypePID;
	private final ServiceTracker<KeyStoreManager, KeyStoreManager>
		_serviceTracker;
	private Function<ServiceReference<?>, String> _valuesFunction;

}