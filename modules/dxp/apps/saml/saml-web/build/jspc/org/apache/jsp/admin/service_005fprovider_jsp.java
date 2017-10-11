package org.apache.jsp.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.liferay.portal.kernel.language.UnicodeLanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.saml.constants.SamlWebKeys;
import com.liferay.saml.persistence.exception.DuplicateSamlIdpSpConnectionSamlSpEntityIdException;
import com.liferay.saml.persistence.exception.DuplicateSamlSpIdpConnectionSamlIdpEntityIdException;
import com.liferay.saml.persistence.exception.SamlIdpSpConnectionMetadataUrlException;
import com.liferay.saml.persistence.exception.SamlIdpSpConnectionMetadataXmlException;
import com.liferay.saml.persistence.exception.SamlIdpSpConnectionNameException;
import com.liferay.saml.persistence.exception.SamlIdpSpConnectionSamlSpEntityIdException;
import com.liferay.saml.persistence.exception.SamlSpIdpConnectionMetadataUrlException;
import com.liferay.saml.persistence.exception.SamlSpIdpConnectionMetadataXmlException;
import com.liferay.saml.persistence.exception.SamlSpIdpConnectionSamlIdpEntityIdException;
import com.liferay.saml.persistence.model.SamlIdpSpConnection;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.runtime.certificate.CertificateTool;
import com.liferay.saml.runtime.configuration.SamlProviderConfiguration;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.exception.CertificateKeyPasswordException;
import com.liferay.saml.util.NameIdTypeValues;
import com.liferay.saml.util.PortletPropsKeys;
import com.liferay.saml.web.internal.util.NameIdTypeValuesUtilHelper;
import com.liferay.taglib.search.ResultRow;
import java.security.InvalidParameterException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.List;
import javax.portlet.PortletURL;

public final class service_005fprovider_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList<String>(1);
    _jspx_dependants.add("/init.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_portlet_actionURL_var_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_aui_button_value_type_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_aui_input_value_type_name_label_helpMessage_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_aui_input_value_name_label_helpMessage_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_portlet_param_value_name_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_liferay$1theme_defineObjects_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_aui_form_action;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_portlet_defineObjects_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_aui_fieldset_label;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_aui_button$1row;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_portlet_actionURL_var_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_aui_button_value_type_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_aui_input_value_type_name_label_helpMessage_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_aui_input_value_name_label_helpMessage_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_portlet_param_value_name_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_liferay$1theme_defineObjects_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_aui_form_action = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_portlet_defineObjects_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_aui_fieldset_label = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_aui_button$1row = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_portlet_actionURL_var_name.release();
    _jspx_tagPool_aui_button_value_type_nobody.release();
    _jspx_tagPool_aui_input_value_type_name_label_helpMessage_nobody.release();
    _jspx_tagPool_aui_input_value_name_label_helpMessage_nobody.release();
    _jspx_tagPool_portlet_param_value_name_nobody.release();
    _jspx_tagPool_liferay$1theme_defineObjects_nobody.release();
    _jspx_tagPool_aui_form_action.release();
    _jspx_tagPool_portlet_defineObjects_nobody.release();
    _jspx_tagPool_aui_fieldset_label.release();
    _jspx_tagPool_aui_button$1row.release();
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write('\n');
      out.write('\n');
      out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
      //  liferay-theme:defineObjects
      com.liferay.taglib.theme.DefineObjectsTag _jspx_th_liferay$1theme_defineObjects_0 = (com.liferay.taglib.theme.DefineObjectsTag) _jspx_tagPool_liferay$1theme_defineObjects_nobody.get(com.liferay.taglib.theme.DefineObjectsTag.class);
      _jspx_th_liferay$1theme_defineObjects_0.setPageContext(_jspx_page_context);
      _jspx_th_liferay$1theme_defineObjects_0.setParent(null);
      int _jspx_eval_liferay$1theme_defineObjects_0 = _jspx_th_liferay$1theme_defineObjects_0.doStartTag();
      if (_jspx_th_liferay$1theme_defineObjects_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_liferay$1theme_defineObjects_nobody.reuse(_jspx_th_liferay$1theme_defineObjects_0);
        return;
      }
      _jspx_tagPool_liferay$1theme_defineObjects_nobody.reuse(_jspx_th_liferay$1theme_defineObjects_0);
      com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay = null;
      com.liferay.portal.kernel.model.Company company = null;
      com.liferay.portal.kernel.model.Account account = null;
      com.liferay.portal.kernel.model.User user = null;
      com.liferay.portal.kernel.model.User realUser = null;
      com.liferay.portal.kernel.model.Contact contact = null;
      com.liferay.portal.kernel.model.Layout layout = null;
      java.util.List layouts = null;
      java.lang.Long plid = null;
      com.liferay.portal.kernel.model.LayoutTypePortlet layoutTypePortlet = null;
      java.lang.Long scopeGroupId = null;
      com.liferay.portal.kernel.security.permission.PermissionChecker permissionChecker = null;
      java.util.Locale locale = null;
      java.util.TimeZone timeZone = null;
      com.liferay.portal.kernel.model.Theme theme = null;
      com.liferay.portal.kernel.model.ColorScheme colorScheme = null;
      com.liferay.portal.kernel.theme.PortletDisplay portletDisplay = null;
      java.lang.Long portletGroupId = null;
      themeDisplay = (com.liferay.portal.kernel.theme.ThemeDisplay) _jspx_page_context.findAttribute("themeDisplay");
      company = (com.liferay.portal.kernel.model.Company) _jspx_page_context.findAttribute("company");
      account = (com.liferay.portal.kernel.model.Account) _jspx_page_context.findAttribute("account");
      user = (com.liferay.portal.kernel.model.User) _jspx_page_context.findAttribute("user");
      realUser = (com.liferay.portal.kernel.model.User) _jspx_page_context.findAttribute("realUser");
      contact = (com.liferay.portal.kernel.model.Contact) _jspx_page_context.findAttribute("contact");
      layout = (com.liferay.portal.kernel.model.Layout) _jspx_page_context.findAttribute("layout");
      layouts = (java.util.List) _jspx_page_context.findAttribute("layouts");
      plid = (java.lang.Long) _jspx_page_context.findAttribute("plid");
      layoutTypePortlet = (com.liferay.portal.kernel.model.LayoutTypePortlet) _jspx_page_context.findAttribute("layoutTypePortlet");
      scopeGroupId = (java.lang.Long) _jspx_page_context.findAttribute("scopeGroupId");
      permissionChecker = (com.liferay.portal.kernel.security.permission.PermissionChecker) _jspx_page_context.findAttribute("permissionChecker");
      locale = (java.util.Locale) _jspx_page_context.findAttribute("locale");
      timeZone = (java.util.TimeZone) _jspx_page_context.findAttribute("timeZone");
      theme = (com.liferay.portal.kernel.model.Theme) _jspx_page_context.findAttribute("theme");
      colorScheme = (com.liferay.portal.kernel.model.ColorScheme) _jspx_page_context.findAttribute("colorScheme");
      portletDisplay = (com.liferay.portal.kernel.theme.PortletDisplay) _jspx_page_context.findAttribute("portletDisplay");
      portletGroupId = (java.lang.Long) _jspx_page_context.findAttribute("portletGroupId");
      out.write('\n');
      out.write('\n');
      //  portlet:defineObjects
      com.liferay.taglib.portlet.DefineObjectsTag _jspx_th_portlet_defineObjects_0 = (com.liferay.taglib.portlet.DefineObjectsTag) _jspx_tagPool_portlet_defineObjects_nobody.get(com.liferay.taglib.portlet.DefineObjectsTag.class);
      _jspx_th_portlet_defineObjects_0.setPageContext(_jspx_page_context);
      _jspx_th_portlet_defineObjects_0.setParent(null);
      int _jspx_eval_portlet_defineObjects_0 = _jspx_th_portlet_defineObjects_0.doStartTag();
      if (_jspx_th_portlet_defineObjects_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_portlet_defineObjects_nobody.reuse(_jspx_th_portlet_defineObjects_0);
        return;
      }
      _jspx_tagPool_portlet_defineObjects_nobody.reuse(_jspx_th_portlet_defineObjects_0);
      javax.portlet.ActionRequest actionRequest = null;
      javax.portlet.ActionResponse actionResponse = null;
      javax.portlet.EventRequest eventRequest = null;
      javax.portlet.EventResponse eventResponse = null;
      com.liferay.portal.kernel.portlet.LiferayPortletRequest liferayPortletRequest = null;
      com.liferay.portal.kernel.portlet.LiferayPortletResponse liferayPortletResponse = null;
      javax.portlet.PortletConfig portletConfig = null;
      java.lang.String portletName = null;
      javax.portlet.PortletPreferences portletPreferences = null;
      java.util.Map portletPreferencesValues = null;
      javax.portlet.PortletSession portletSession = null;
      java.util.Map portletSessionScope = null;
      javax.portlet.RenderRequest renderRequest = null;
      javax.portlet.RenderResponse renderResponse = null;
      javax.portlet.ResourceRequest resourceRequest = null;
      javax.portlet.ResourceResponse resourceResponse = null;
      actionRequest = (javax.portlet.ActionRequest) _jspx_page_context.findAttribute("actionRequest");
      actionResponse = (javax.portlet.ActionResponse) _jspx_page_context.findAttribute("actionResponse");
      eventRequest = (javax.portlet.EventRequest) _jspx_page_context.findAttribute("eventRequest");
      eventResponse = (javax.portlet.EventResponse) _jspx_page_context.findAttribute("eventResponse");
      liferayPortletRequest = (com.liferay.portal.kernel.portlet.LiferayPortletRequest) _jspx_page_context.findAttribute("liferayPortletRequest");
      liferayPortletResponse = (com.liferay.portal.kernel.portlet.LiferayPortletResponse) _jspx_page_context.findAttribute("liferayPortletResponse");
      portletConfig = (javax.portlet.PortletConfig) _jspx_page_context.findAttribute("portletConfig");
      portletName = (java.lang.String) _jspx_page_context.findAttribute("portletName");
      portletPreferences = (javax.portlet.PortletPreferences) _jspx_page_context.findAttribute("portletPreferences");
      portletPreferencesValues = (java.util.Map) _jspx_page_context.findAttribute("portletPreferencesValues");
      portletSession = (javax.portlet.PortletSession) _jspx_page_context.findAttribute("portletSession");
      portletSessionScope = (java.util.Map) _jspx_page_context.findAttribute("portletSessionScope");
      renderRequest = (javax.portlet.RenderRequest) _jspx_page_context.findAttribute("renderRequest");
      renderResponse = (javax.portlet.RenderResponse) _jspx_page_context.findAttribute("renderResponse");
      resourceRequest = (javax.portlet.ResourceRequest) _jspx_page_context.findAttribute("resourceRequest");
      resourceResponse = (javax.portlet.ResourceResponse) _jspx_page_context.findAttribute("resourceResponse");
      out.write('\n');
      out.write('\n');

String currentURL = PortalUtil.getCurrentURL(request);

NameIdTypeValues nameIdTypeValues = NameIdTypeValuesUtilHelper.getNameIdTypeValues();

SamlProviderConfigurationHelper samlProviderConfigurationHelper = (SamlProviderConfigurationHelper)request.getAttribute(ClassUtil.getClassName(SamlProviderConfigurationHelper.class));

SamlProviderConfiguration samlProviderConfiguration = null;

if (samlProviderConfigurationHelper != null) {
	samlProviderConfiguration = samlProviderConfigurationHelper.getSamlProviderConfiguration();
}

      out.write('\n');
      out.write('\n');
      //  portlet:actionURL
      com.liferay.taglib.portlet.ActionURLTag _jspx_th_portlet_actionURL_0 = (com.liferay.taglib.portlet.ActionURLTag) _jspx_tagPool_portlet_actionURL_var_name.get(com.liferay.taglib.portlet.ActionURLTag.class);
      _jspx_th_portlet_actionURL_0.setPageContext(_jspx_page_context);
      _jspx_th_portlet_actionURL_0.setParent(null);
      _jspx_th_portlet_actionURL_0.setName("/admin/updateServiceProvider");
      _jspx_th_portlet_actionURL_0.setVar("updateServiceProviderURL");
      int _jspx_eval_portlet_actionURL_0 = _jspx_th_portlet_actionURL_0.doStartTag();
      if (_jspx_eval_portlet_actionURL_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        out.write('\n');
        out.write('	');
        if (_jspx_meth_portlet_param_0((javax.servlet.jsp.tagext.JspTag) _jspx_th_portlet_actionURL_0, _jspx_page_context))
          return;
        out.write('\n');
      }
      if (_jspx_th_portlet_actionURL_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_portlet_actionURL_var_name.reuse(_jspx_th_portlet_actionURL_0);
        return;
      }
      _jspx_tagPool_portlet_actionURL_var_name.reuse(_jspx_th_portlet_actionURL_0);
      java.lang.String updateServiceProviderURL = null;
      updateServiceProviderURL = (java.lang.String) _jspx_page_context.findAttribute("updateServiceProviderURL");
      out.write('\n');
      out.write('\n');
      //  aui:form
      com.liferay.taglib.aui.FormTag _jspx_th_aui_form_0 = (com.liferay.taglib.aui.FormTag) _jspx_tagPool_aui_form_action.get(com.liferay.taglib.aui.FormTag.class);
      _jspx_th_aui_form_0.setPageContext(_jspx_page_context);
      _jspx_th_aui_form_0.setParent(null);
      _jspx_th_aui_form_0.setAction( updateServiceProviderURL );
      int _jspx_eval_aui_form_0 = _jspx_th_aui_form_0.doStartTag();
      if (_jspx_eval_aui_form_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        out.write('\n');
        out.write('	');
        //  aui:fieldset
        com.liferay.taglib.aui.FieldsetTag _jspx_th_aui_fieldset_0 = (com.liferay.taglib.aui.FieldsetTag) _jspx_tagPool_aui_fieldset_label.get(com.liferay.taglib.aui.FieldsetTag.class);
        _jspx_th_aui_fieldset_0.setPageContext(_jspx_page_context);
        _jspx_th_aui_fieldset_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_aui_form_0);
        _jspx_th_aui_fieldset_0.setLabel("general");
        int _jspx_eval_aui_fieldset_0 = _jspx_th_aui_fieldset_0.doStartTag();
        if (_jspx_eval_aui_fieldset_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          out.write("\n\t\t");
          //  aui:input
          com.liferay.taglib.aui.InputTag _jspx_th_aui_input_0 = (com.liferay.taglib.aui.InputTag) _jspx_tagPool_aui_input_value_type_name_label_helpMessage_nobody.get(com.liferay.taglib.aui.InputTag.class);
          _jspx_th_aui_input_0.setPageContext(_jspx_page_context);
          _jspx_th_aui_input_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_aui_fieldset_0);
          _jspx_th_aui_input_0.setHelpMessage("saml-sp-assertion-signature-required-description");
          _jspx_th_aui_input_0.setLabel("saml-sp-assertion-signature-required");
          _jspx_th_aui_input_0.setName( "settings--" + PortletPropsKeys.SAML_SP_ASSERTION_SIGNATURE_REQUIRED + "--" );
          _jspx_th_aui_input_0.setType("checkbox");
          _jspx_th_aui_input_0.setValue( samlProviderConfiguration.assertionSignatureRequired() );
          int _jspx_eval_aui_input_0 = _jspx_th_aui_input_0.doStartTag();
          if (_jspx_th_aui_input_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_aui_input_value_type_name_label_helpMessage_nobody.reuse(_jspx_th_aui_input_0);
            return;
          }
          _jspx_tagPool_aui_input_value_type_name_label_helpMessage_nobody.reuse(_jspx_th_aui_input_0);
          out.write("\n\n\t\t");
          //  aui:input
          com.liferay.taglib.aui.InputTag _jspx_th_aui_input_1 = (com.liferay.taglib.aui.InputTag) _jspx_tagPool_aui_input_value_name_label_helpMessage_nobody.get(com.liferay.taglib.aui.InputTag.class);
          _jspx_th_aui_input_1.setPageContext(_jspx_page_context);
          _jspx_th_aui_input_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_aui_fieldset_0);
          _jspx_th_aui_input_1.setHelpMessage("saml-sp-clock-skew-description");
          _jspx_th_aui_input_1.setLabel("saml-sp-clock-skew");
          _jspx_th_aui_input_1.setName( "settings--" + PortletPropsKeys.SAML_SP_CLOCK_SKEW + "--" );
          _jspx_th_aui_input_1.setValue( samlProviderConfiguration.clockSkew() );
          int _jspx_eval_aui_input_1 = _jspx_th_aui_input_1.doStartTag();
          if (_jspx_th_aui_input_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_aui_input_value_name_label_helpMessage_nobody.reuse(_jspx_th_aui_input_1);
            return;
          }
          _jspx_tagPool_aui_input_value_name_label_helpMessage_nobody.reuse(_jspx_th_aui_input_1);
          out.write("\n\n\t\t");
          //  aui:input
          com.liferay.taglib.aui.InputTag _jspx_th_aui_input_2 = (com.liferay.taglib.aui.InputTag) _jspx_tagPool_aui_input_value_type_name_label_helpMessage_nobody.get(com.liferay.taglib.aui.InputTag.class);
          _jspx_th_aui_input_2.setPageContext(_jspx_page_context);
          _jspx_th_aui_input_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_aui_fieldset_0);
          _jspx_th_aui_input_2.setHelpMessage("saml-sp-ldap-import-enabled-description");
          _jspx_th_aui_input_2.setLabel("saml-sp-ldap-import-enabled");
          _jspx_th_aui_input_2.setName( "settings--" + PortletPropsKeys.SAML_SP_LDAP_IMPORT_ENABLED + "--" );
          _jspx_th_aui_input_2.setType("checkbox");
          _jspx_th_aui_input_2.setValue( samlProviderConfiguration.ldapImportEnabled() );
          int _jspx_eval_aui_input_2 = _jspx_th_aui_input_2.doStartTag();
          if (_jspx_th_aui_input_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_aui_input_value_type_name_label_helpMessage_nobody.reuse(_jspx_th_aui_input_2);
            return;
          }
          _jspx_tagPool_aui_input_value_type_name_label_helpMessage_nobody.reuse(_jspx_th_aui_input_2);
          out.write("\n\n\t\t");
          //  aui:input
          com.liferay.taglib.aui.InputTag _jspx_th_aui_input_3 = (com.liferay.taglib.aui.InputTag) _jspx_tagPool_aui_input_value_type_name_label_helpMessage_nobody.get(com.liferay.taglib.aui.InputTag.class);
          _jspx_th_aui_input_3.setPageContext(_jspx_page_context);
          _jspx_th_aui_input_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_aui_fieldset_0);
          _jspx_th_aui_input_3.setHelpMessage("saml-sp-sign-authn-request-description");
          _jspx_th_aui_input_3.setLabel("saml-sp-sign-authn-request");
          _jspx_th_aui_input_3.setName( "settings--" + PortletPropsKeys.SAML_SP_SIGN_AUTHN_REQUEST + "--" );
          _jspx_th_aui_input_3.setType("checkbox");
          _jspx_th_aui_input_3.setValue( samlProviderConfiguration.signAuthnRequest() );
          int _jspx_eval_aui_input_3 = _jspx_th_aui_input_3.doStartTag();
          if (_jspx_th_aui_input_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_aui_input_value_type_name_label_helpMessage_nobody.reuse(_jspx_th_aui_input_3);
            return;
          }
          _jspx_tagPool_aui_input_value_type_name_label_helpMessage_nobody.reuse(_jspx_th_aui_input_3);
          out.write("\n\n\t\t");
          //  aui:input
          com.liferay.taglib.aui.InputTag _jspx_th_aui_input_4 = (com.liferay.taglib.aui.InputTag) _jspx_tagPool_aui_input_value_type_name_label_helpMessage_nobody.get(com.liferay.taglib.aui.InputTag.class);
          _jspx_th_aui_input_4.setPageContext(_jspx_page_context);
          _jspx_th_aui_input_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_aui_fieldset_0);
          _jspx_th_aui_input_4.setHelpMessage("saml-sign-metadata-description");
          _jspx_th_aui_input_4.setLabel("saml-sign-metadata");
          _jspx_th_aui_input_4.setName( "settings--" + PortletPropsKeys.SAML_SIGN_METADATA + "--" );
          _jspx_th_aui_input_4.setType("checkbox");
          _jspx_th_aui_input_4.setValue( samlProviderConfiguration.signMetadata() );
          int _jspx_eval_aui_input_4 = _jspx_th_aui_input_4.doStartTag();
          if (_jspx_th_aui_input_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_aui_input_value_type_name_label_helpMessage_nobody.reuse(_jspx_th_aui_input_4);
            return;
          }
          _jspx_tagPool_aui_input_value_type_name_label_helpMessage_nobody.reuse(_jspx_th_aui_input_4);
          out.write("\n\n\t\t");
          //  aui:input
          com.liferay.taglib.aui.InputTag _jspx_th_aui_input_5 = (com.liferay.taglib.aui.InputTag) _jspx_tagPool_aui_input_value_type_name_label_helpMessage_nobody.get(com.liferay.taglib.aui.InputTag.class);
          _jspx_th_aui_input_5.setPageContext(_jspx_page_context);
          _jspx_th_aui_input_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_aui_fieldset_0);
          _jspx_th_aui_input_5.setHelpMessage("saml-ssl-required-description");
          _jspx_th_aui_input_5.setLabel("saml-ssl-required");
          _jspx_th_aui_input_5.setName( "settings--" + PortletPropsKeys.SAML_SSL_REQUIRED + "--" );
          _jspx_th_aui_input_5.setType("checkbox");
          _jspx_th_aui_input_5.setValue( samlProviderConfiguration.sslRequired() );
          int _jspx_eval_aui_input_5 = _jspx_th_aui_input_5.doStartTag();
          if (_jspx_th_aui_input_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_aui_input_value_type_name_label_helpMessage_nobody.reuse(_jspx_th_aui_input_5);
            return;
          }
          _jspx_tagPool_aui_input_value_type_name_label_helpMessage_nobody.reuse(_jspx_th_aui_input_5);
          out.write('\n');
          out.write('	');
        }
        if (_jspx_th_aui_fieldset_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
          _jspx_tagPool_aui_fieldset_label.reuse(_jspx_th_aui_fieldset_0);
          return;
        }
        _jspx_tagPool_aui_fieldset_label.reuse(_jspx_th_aui_fieldset_0);
        out.write("\n\n\t");
        if (_jspx_meth_aui_button$1row_0((javax.servlet.jsp.tagext.JspTag) _jspx_th_aui_form_0, _jspx_page_context))
          return;
        out.write('\n');
      }
      if (_jspx_th_aui_form_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_aui_form_action.reuse(_jspx_th_aui_form_0);
        return;
      }
      _jspx_tagPool_aui_form_action.reuse(_jspx_th_aui_form_0);
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_portlet_param_0(javax.servlet.jsp.tagext.JspTag _jspx_th_portlet_actionURL_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  portlet:param
    com.liferay.taglib.util.ParamTag _jspx_th_portlet_param_0 = (com.liferay.taglib.util.ParamTag) _jspx_tagPool_portlet_param_value_name_nobody.get(com.liferay.taglib.util.ParamTag.class);
    _jspx_th_portlet_param_0.setPageContext(_jspx_page_context);
    _jspx_th_portlet_param_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_portlet_actionURL_0);
    _jspx_th_portlet_param_0.setName("tabs1");
    _jspx_th_portlet_param_0.setValue("service-provider");
    int _jspx_eval_portlet_param_0 = _jspx_th_portlet_param_0.doStartTag();
    if (_jspx_th_portlet_param_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_portlet_param_value_name_nobody.reuse(_jspx_th_portlet_param_0);
      return true;
    }
    _jspx_tagPool_portlet_param_value_name_nobody.reuse(_jspx_th_portlet_param_0);
    return false;
  }

  private boolean _jspx_meth_aui_button$1row_0(javax.servlet.jsp.tagext.JspTag _jspx_th_aui_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  aui:button-row
    com.liferay.taglib.aui.ButtonRowTag _jspx_th_aui_button$1row_0 = (com.liferay.taglib.aui.ButtonRowTag) _jspx_tagPool_aui_button$1row.get(com.liferay.taglib.aui.ButtonRowTag.class);
    _jspx_th_aui_button$1row_0.setPageContext(_jspx_page_context);
    _jspx_th_aui_button$1row_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_aui_form_0);
    int _jspx_eval_aui_button$1row_0 = _jspx_th_aui_button$1row_0.doStartTag();
    if (_jspx_eval_aui_button$1row_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      out.write("\n\t\t");
      if (_jspx_meth_aui_button_0((javax.servlet.jsp.tagext.JspTag) _jspx_th_aui_button$1row_0, _jspx_page_context))
        return true;
      out.write('\n');
      out.write('	');
    }
    if (_jspx_th_aui_button$1row_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_aui_button$1row.reuse(_jspx_th_aui_button$1row_0);
      return true;
    }
    _jspx_tagPool_aui_button$1row.reuse(_jspx_th_aui_button$1row_0);
    return false;
  }

  private boolean _jspx_meth_aui_button_0(javax.servlet.jsp.tagext.JspTag _jspx_th_aui_button$1row_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  aui:button
    com.liferay.taglib.aui.ButtonTag _jspx_th_aui_button_0 = (com.liferay.taglib.aui.ButtonTag) _jspx_tagPool_aui_button_value_type_nobody.get(com.liferay.taglib.aui.ButtonTag.class);
    _jspx_th_aui_button_0.setPageContext(_jspx_page_context);
    _jspx_th_aui_button_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_aui_button$1row_0);
    _jspx_th_aui_button_0.setType("submit");
    _jspx_th_aui_button_0.setValue("save");
    int _jspx_eval_aui_button_0 = _jspx_th_aui_button_0.doStartTag();
    if (_jspx_th_aui_button_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_aui_button_value_type_nobody.reuse(_jspx_th_aui_button_0);
      return true;
    }
    _jspx_tagPool_aui_button_value_type_nobody.reuse(_jspx_th_aui_button_0);
    return false;
  }
}
