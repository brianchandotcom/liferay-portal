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

import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.render.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.render.DDMFormRendererUtil;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.frontend.js.portlet.extender.internal.portlet.JSPortlet;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Dictionary;
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
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;

/**
 * @author Gustavo Mantuan
 */

public class PortletExtenderConfigurationAction
    extends DefaultConfigurationAction
    implements ManagedService {

  public PortletExtenderConfigurationAction(String name) {
    _name = name;
  }

  @Override
  public void include(
      PortletConfig portletConfig, HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {

    ThemeDisplay themeDisplay =
        (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
    PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

    DDMFormRenderingContext ddmFormRenderingContext = new DDMFormRenderingContext();
    ddmFormRenderingContext.setShowSubmitButton(true);

    DDMFormFieldRenderingContext ddmFormFieldRenderingContext = new DDMFormFieldRenderingContext();
    ddmFormFieldRenderingContext.setHttpServletRequest(request);
    ddmFormFieldRenderingContext.setHttpServletResponse(response);
    ddmFormFieldRenderingContext.setLocale(themeDisplay.getLocale());
    ddmFormFieldRenderingContext.setReadOnly(false);
    ddmFormFieldRenderingContext.setMode("edit");
    ddmFormFieldRenderingContext.setShowEmptyFieldLabel(true);

    JSONObject jsonValues = JSONFactoryUtil.createJSONObject();

    try (InputStream configurationStreamNew = getServletContext(request)
        .getResourceAsStream("META-INF/resources/configuration.json")) {

      String configurationString = StringUtil.read(configurationStreamNew);
      DDMFormRenderer _ddmFormRenderer = DDMFormRendererUtil.getDDMFormRenderer();
      DDMForm ddmForm = DDMUtil.getDDMForm(configurationString);

      JSONArray ddmFormObjectKeys =
          JSONFactoryUtil.createJSONArray(
              JSONFactoryUtil.createJSONObject(configurationString).getJSONArray("fields")
                  .toString());

      request.setAttribute(_CONFIGURATION, ddmFormObjectKeys);

      PortletPreferences portletPreferences =
          PortletPreferencesFactoryUtil
              .getExistingPortletSetup(
                  themeDisplay.getLayout(),
                  portletDisplay.getPortletResource());

      portletPreferences.getMap().forEach((key, value) -> {
        for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {

          if (key.contains(ddmFormField.getName())) {
            if (!("select").equals(ddmFormField.getType())
                && !("radio").equals(ddmFormField.getType())
                && !("ddm-date").equals(ddmFormField.getType())) {
              LocalizedValue defaultValue = new LocalizedValue();
              defaultValue.setDefaultLocale(themeDisplay.getLocale());
              defaultValue.addString(themeDisplay.getLocale(), value[0]);
              ddmFormField.setPredefinedValue(defaultValue);
            }
          }

        }

        jsonValues.put(key, value);

      });

      String ddmFormRendered = _ddmFormRenderer.render(ddmForm, ddmFormFieldRenderingContext)
          .trim();

      generateConfigurationFormFieldsByJson(ddmFormObjectKeys,
           portletDisplay.getNamespace(),
          response.getWriter(),
          getActionURL(request, portletDisplay),
          ddmFormRendered);

    } catch (Exception e) {
      _log.error(
          "Unable to process configuration.json of " +
              portletDisplay.getPortletResource(),
          e);
    }
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
      setPreference(actionRequest, pair.getKey(), pair.getValue());
    }

    super.processAction(portletConfig, actionRequest, actionResponse);
  }

  @Override
  public void updated(Dictionary<String, ?> dictionary)
      throws ConfigurationException {
  }

  @Override
  protected Collection<PortletMode> getNextPossiblePortletModes(
      RenderRequest request) {
    return super.getNextPossiblePortletModes(request);
  }

  private String appendPortletName(String portletName, String whatToAppend) {
    return portletName + whatToAppend;
  }

  private void generateConfigurationFormFieldsByJson(JSONArray jsonObject,
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

    printWriter.println(ddmFormRendered);

    printWriter.println(String.format(" <div class=\"button-holder dialog-footer\">\n"
        + "        <button class=\"btn btn-primary btn-default\" id=\"form-button-submit\" type=\"submit\">\n"
        + "         <span class=\"lfr-btn-label\">Save</span>\n"
        + "        </button>\n"
        + "      </div>"));

    printWriter.println("</form>");

    printWriter.flush();
  }

  private String getActionURL(HttpServletRequest request, PortletDisplay portletDisplay) {
    PortletURL actionURL = PortletURLFactoryUtil.create(request,
        "com_liferay_portlet_configuration_web_portlet_PortletConfigurationPortlet",
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


  private static final String _CONFIGURATION = "configurationObject";

  private static final String _FORM_DATA = "formDate";

  private static final Log _log =
      LogFactoryUtil.getLog(PortletExtenderConfigurationAction.class);

  private final String _name;

}
