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

package com.liferay.portal.search.tuning.blueprints.admin.web.internal.portlet.action;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.constants.BlueprintsAdminMVCCommandNames;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.constants.BlueprintsAdminWebKeys;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.util.BlueprintsAdminRequestUtil;
import com.liferay.portal.search.tuning.blueprints.constants.BlueprintsPortletKeys;
import com.liferay.portal.search.tuning.blueprints.exception.ElementValidationException;
import com.liferay.portal.search.tuning.blueprints.model.Element;
import com.liferay.portal.search.tuning.blueprints.service.ElementService;

import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + BlueprintsPortletKeys.BLUEPRINTS_ADMIN,
		"mvc.command.name=" + BlueprintsAdminMVCCommandNames.COPY_ELEMENT
	},
	service = MVCActionCommand.class
)
public class CopyElementMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		Optional<Element> elementOptional =
			BlueprintsAdminRequestUtil.getElement(
				actionRequest, actionResponse);

		if (!elementOptional.isPresent()) {
			return;
		}

		_createCopy(actionRequest, actionResponse, elementOptional.get());
	}

	private void _createCopy(
		ActionRequest actionRequest, ActionResponse actionResponse,
		Element sourceElement) {

		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				Element.class.getName(), actionRequest);

			_elementService.addCompanyElement(
				_getTargetTitleMap(sourceElement),
				sourceElement.getDescriptionMap(),
				sourceElement.getConfiguration(), sourceElement.getType(),
				serviceContext);

			sendRedirect(actionRequest, actionResponse);
		}
		catch (ElementValidationException elementValidationException) {
			_log.error(
				elementValidationException.getMessage(),
				elementValidationException);

			List<String> errors = elementValidationException.getErrors();

			errors.forEach(key -> SessionErrors.add(actionRequest, key));
		}
		catch (PortalException portalException) {
			_log.error(portalException.getMessage(), portalException);

			SessionErrors.add(
				actionRequest, BlueprintsAdminWebKeys.ERROR,
				portalException.getMessage());
		}
		catch (IOException ioException) {
			_log.error(ioException.getMessage(), ioException);

			SessionErrors.add(
				actionRequest, BlueprintsAdminWebKeys.ERROR,
				ioException.getMessage());
		}
	}

	private Map<Locale, String> _getTargetTitleMap(Element element) {
		Map<Locale, String> sourceTitleMap = element.getTitleMap();
		Map<Locale, String> targetTitleMap = new HashMap<>();

		for (Map.Entry<Locale, String> entry : sourceTitleMap.entrySet()) {
			StringBundler sb = new StringBundler(4);

			sb.append(entry.getValue());
			sb.append(" (");
			sb.append(_language.get(entry.getKey(), "copy"));
			sb.append(")");

			targetTitleMap.put(entry.getKey(), sb.toString());
		}

		return targetTitleMap;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CopyElementMVCActionCommand.class);

	@Reference
	private ElementService _elementService;

	@Reference
	private Language _language;

}