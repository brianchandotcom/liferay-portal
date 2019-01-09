/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */


package com.liferay.frontend.js.portlet.extender.configuration;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.render.DDMFormRendererUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.WebKeys;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gustavo Mantuan
 */

public class PortletExtenderConfigurationAction
    extends DefaultConfigurationAction {


  public PortletExtenderConfigurationAction(String name, DDMForm ddmForm,
      JSONArray configurationObject) {
    _name = name;
    _ddmForm = ddmForm;
    _ddmFormObjectKeys = configurationObject;
  }

  @Override
  public void include(
      PortletConfig portletConfig, HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {

    ThemeDisplay themeDisplay =
        (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

    PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

    DDMFormFieldRenderingContext ddmFormFieldRenderingContext = getDdmFormFieldRenderingContext(
        request, response, themeDisplay, portletDisplay);

    setPortletPreferencesToDDMFormValues(themeDisplay, portletDisplay);

    generateConfigurationFormByDDMFormRender(_ddmFormObjectKeys,
        portletDisplay.getNamespace(),
        response.getWriter(),
        getActionURL(request, portletDisplay),
        DDMFormRendererUtil.render(_ddmForm, ddmFormFieldRenderingContext));
  }

  @Override
  public void processAction(
      PortletConfig portletConfig, ActionRequest actionRequest,
      ActionResponse actionResponse)
      throws Exception {

    Map<String, String[]> parameterValues = actionRequest.getParameterMap();
    Iterator<Entry<String, String[]>> iterator = parameterValues.entrySet().iterator();

    while (iterator.hasNext()) {
      Map.Entry<String, String[]> pair = iterator.next();
      if (!pair.getKey().contains("Day") && !pair.getKey().contains("Month") && !pair.getKey()
          .contains("Year")) {
        String key = pair.getKey().split("_INSTANCE")[0];
        setPreference(actionRequest, key, pair.getValue());
      }
    }

    super.processAction(portletConfig, actionRequest, actionResponse);
  }

  @Override
  protected Collection<PortletMode> getNextPossiblePortletModes(
      RenderRequest request) {
    return super.getNextPossiblePortletModes(request);
  }

  private String appendPortletName(String portletName, String whatToAppend) {
    return portletName + whatToAppend;
  }

  private void generateConfigurationFormByDDMFormRender(JSONArray jsonObject,
      String portletName, PrintWriter printWriter,
      String urlConfiguration, String ddmFormRendered) {
    printWriter.println(String.format(
        "<form class='form container container-no-gutters-sm-down container-view' "
            + "method='post' id=\"%s\" name=\"%s\" "
            + "data-fm-namespace=\"%s\""
            + "action=\"%s\">",
        appendPortletName(portletName, "fm"),
        appendPortletName(portletName, "fm"),
        portletName, urlConfiguration));

    printWriter.println(String.format(
        "<input class=\"field form-control\" "
            + "id=\"%s\" name=\"%s\" type=\"hidden\" value=\"%s\"/>",
        appendPortletName(portletName, _FORM_DATA),
        appendPortletName(portletName, _FORM_DATA),
        System.currentTimeMillis()
    ));

    printWriter.println(String.format(
        "<input class=\"field form-control\" "
            + "id=\"%s\" name=\"%s\" type=\"hidden\" value='\"%s\"'/>",
        appendPortletName(portletName, _CONFIGURATION),
        appendPortletName(portletName, _CONFIGURATION),
        jsonObject.toString()
    ));

    printWriter.println(String.format(
        "<input class=\"field form-control\" "
            + "id=\"%s\" name=\"%s\" type=\"hidden\" value=\"%s\"/>",
        appendPortletName(portletName, Constants.CMD),
        appendPortletName(portletName, Constants.CMD),
        Constants.UPDATE
    ));

    printWriter.println(" <div class=\"lfr-form-content\" id=\"portlet-configuration\">\n"
        + "  <div class=\"sheet sheet-lg\" id=\"sheet-portlet\">\n"
        + "    <div class=\"panel-group\" aria-multiselectable=\"true\" role=\"tablist\">");

    printWriter.println(ddmFormRendered);

    printWriter.print("</div></div></div>");

    printWriter.println(" <div class=\"button-holder dialog-footer\">\n"
        + "        <button class=\"btn btn-primary btn-default\" id=\"form-button-submit\" type=\"submit\">\n"
        + "         <span class=\"lfr-btn-label\">Save</span>\n"
        + "        </button>\n"
        + "      </div>");

    printWriter.println("</form>");

    printWriter.flush();
  }

  private String getActionURL(HttpServletRequest request, PortletDisplay portletDisplay) {
    PortletURL actionURL = PortletURLFactoryUtil.create(request,
        portletDisplay.getPortletName(),
        "0");

    actionURL.setParameter("p_p_state", "pop_up");
    actionURL.setParameter("p_p_mode", "view");
    actionURL.setParameter("p_p_lifecycle", "1");
    actionURL.setParameter("portletConfiguration", "true");
    actionURL.setParameter("javax.portlet.action", "editConfiguration");
    actionURL.setParameter("returnToFullPageURL", "/");
    actionURL.setParameter("previewWidth", "");
    actionURL.setParameter("mvcPath", "/edit_configuration.jsp");
    actionURL.setParameter("settingsScope", "portletInstance");
    actionURL.setParameter("portletResource", portletDisplay.getPortletResource());
    actionURL.setParameter("p_auth", AuthTokenUtil.getToken(request));

    return actionURL.toString();
  }

  private DDMFormFieldRenderingContext getDdmFormFieldRenderingContext(HttpServletRequest request,
      HttpServletResponse response, ThemeDisplay themeDisplay, PortletDisplay portletDisplay) {
    DDMFormFieldRenderingContext ddmFormFieldRenderingContext = new DDMFormFieldRenderingContext();
    ddmFormFieldRenderingContext.setHttpServletRequest(request);
    ddmFormFieldRenderingContext.setHttpServletResponse(response);
    ddmFormFieldRenderingContext.setLocale(themeDisplay.getLocale());
    ddmFormFieldRenderingContext.setReadOnly(false);
    ddmFormFieldRenderingContext.setPortletNamespace(portletDisplay.getNamespace());
    ddmFormFieldRenderingContext.setMode("edit");
    ddmFormFieldRenderingContext.setShowEmptyFieldLabel(true);
    ddmFormFieldRenderingContext.setViewMode(true);
    return ddmFormFieldRenderingContext;
  }

  private void setPortletPreferencesToDDMFormValues(ThemeDisplay themeDisplay,
      PortletDisplay portletDisplay) throws PortalException {
    PortletPreferences portletPreferences =
        PortletPreferencesFactoryUtil
            .getExistingPortletSetup(
                themeDisplay.getLayout(),
                portletDisplay.getPortletResource());

    portletPreferences.getMap().forEach((key, values) -> {
      for (DDMFormField ddmFormField : _ddmForm.getDDMFormFields()) {
        LocalizedValue defaultValue = new LocalizedValue();
        defaultValue.setDefaultLocale(themeDisplay.getLocale());

        if (key.contains(ddmFormField.getName())) {
          if ("select".equals(ddmFormField.getType())) {
            JSONArray predefinedValues = JSONFactoryUtil.createJSONArray();
            for (String singleValue : values) {
              predefinedValues.put(singleValue);
            }
            defaultValue.addString(themeDisplay.getLocale(), predefinedValues.toString());
            ddmFormField.setProperty("predefinedValue", defaultValue);
            ddmFormField.setPredefinedValue(defaultValue);
          } else {
            defaultValue.addString(themeDisplay.getLocale(), values[0]);
            ddmFormField.setProperty("predefinedValue", defaultValue);
            ddmFormField.setPredefinedValue(defaultValue);
          }
        }
      }

    });
  }

  private static final String _CONFIGURATION = "configurationObject";

  private static final String _FORM_DATA = "formDate";

  private static final Log _log =
      LogFactoryUtil.getLog(PortletExtenderConfigurationAction.class);

  private final DDMForm _ddmForm;

  private final String _name;

  private JSONArray _ddmFormObjectKeys;
}
