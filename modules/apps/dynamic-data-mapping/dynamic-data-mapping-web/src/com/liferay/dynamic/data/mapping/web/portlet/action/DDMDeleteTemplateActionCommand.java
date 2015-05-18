package com.liferay.dynamic.data.mapping.web.portlet.action;

import com.liferay.dynamic.data.mapping.web.portlet.constants.DDMConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.ActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateServiceUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"action.command.name=ddmDeleteTemplate",
		"javax.portlet.name=" + PortletKeys.DYNAMIC_DATA_MAPPING
	},
	service = { ActionCommand.class }
)
public class DDMDeleteTemplateActionCommand extends DDMBaseActionCommand {

	@Override
	protected void doProcessCommand(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		long[] deleteTemplateIds = null;

		long templateId = ParamUtil.getLong(
			portletRequest, DDMConstants.TEMPLATE_ID);

		if (templateId > 0) {
			deleteTemplateIds = new long[] {templateId};
		}
		else {
			deleteTemplateIds = StringUtil.split(
				ParamUtil.getString(
					portletRequest, DDMConstants.DELETE_TEMPLATE_IDS), 0L);
		}

		for (long deleteTemplateId : deleteTemplateIds) {
			DDMTemplateServiceUtil.deleteTemplate(deleteTemplateId);
		}

		String redirect = ParamUtil.getString(
			portletRequest, DDMConstants.REDIRECT);

		super.setRedirectAttribute(portletRequest, redirect);
	}

}