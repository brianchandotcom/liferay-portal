/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup.internal.verify;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.db.DBResourceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ServiceComponent;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.module.util.BundleUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.service.ServiceComponentLocalService;
import com.liferay.portal.kernel.upgrade.data.cleanup.util.DataCleanupLoggingUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.verify.VerifyProcess;

import java.util.List;
import java.util.Objects;

import org.osgi.framework.Bundle;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Ortiz
 */
@Component(
	property = "run.on.portal.upgrade=true", service = VerifyProcess.class
)
public class ServiceComponentDataCleanupVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		List<ServiceComponent> serviceComponents =
			_serviceComponentLocalService.getServiceComponents(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (ServiceComponent serviceComponent : serviceComponents) {
			String buildNamespace = serviceComponent.getBuildNamespace();

			if (!buildNamespace.contains(StringPool.PERIOD) &&
				!buildNamespace.equals("portal")) {

				_serviceComponentLocalService.deleteServiceComponent(
					serviceComponent);

				DataCleanupLoggingUtil.logDelete(
					_log, 1, "ServiceComponent",
					buildNamespace + " is a portletShortName");
			}
			else if (buildNamespace.startsWith("com.liferay.")) {
				Bundle bundle = BundleUtil.getBundle(
					SystemBundleUtil.getBundleContext(), buildNamespace);

				if (bundle == null) {
					_serviceComponentLocalService.deleteServiceComponent(
						serviceComponent);

					DataCleanupLoggingUtil.logDelete(
						_log, 1, "ServiceComponent",
						buildNamespace +
							" does not match with any existing bundle");

					continue;
				}

				if (!Objects.equals(
						serviceComponent.getData(), _generateXML(bundle))) {

					_log.error(
						"Content of table ServiceComponent for bundle " +
							buildNamespace + " is outdated");
				}
			}
		}
	}

	private String _generateXML(Bundle bundle) throws Exception {
		Document document = SAXReaderUtil.createDocument(StringPool.UTF8);

		Element dataElement = document.addElement("data");

		Element tablesSQLElement = dataElement.addElement("tables-sql");

		tablesSQLElement.addCDATA(DBResourceUtil.getModuleTablesSQL(bundle));

		Element sequencesSQLElement = dataElement.addElement("sequences-sql");

		sequencesSQLElement.addCDATA(
			DBResourceUtil.getModuleSequencesSQL(bundle));

		Element indexesSQLElement = dataElement.addElement("indexes-sql");

		indexesSQLElement.addCDATA(DBResourceUtil.getModuleIndexesSQL(bundle));

		return document.formattedString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServiceComponentDataCleanupVerifyProcess.class);

	@Reference(target = ModuleServiceLifecycle.PORTLETS_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private ServiceComponentLocalService _serviceComponentLocalService;

}