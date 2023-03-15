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

package com.liferay.osb.faro.functional.test.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * For storing long, ambiguous, or version specific DXP XPath or URL path
 * strings
 *
 * @author Cheryl Tang
 */
public class DxpStringPool {

	public static final String AC_INSTANCE_SETTINGS_URL_BASE =
		_createAcInstanceSettingsUrlBase();

	public static final String AC_INSTANCE_SETTINGS_URL_PATH =
		AC_INSTANCE_SETTINGS_URL_BASE + "0-analytics-cloud-connection";

	public static final String ADD_OAUTH_APP_BUTTON_XPATH =
		"//a[@title='Add OAuth 2 Application']";

	public static final String ADD_SITES_URL_PATH = StringBundler.concat(
		"/group/control_panel/manage/-/sites/sites/select_site?_com_liferay_",
		"site_admin_web_portlet_SiteAdminPortlet_redirect=%2Fgroup%",
		"3DR83NOhx7&p_p_auth=R83NOhx7",
		"2Fcontrol_panel%2Fmanage%2F-%2Fsites%2Fsites%3Fp_p_auth%");

	public static final String ANALYTICS_SCOPE_EXPANDED_XPATH =
		"//span[text()='Analytics']/parent::a[@aria-expanded='true']";

	public static final String ANALYTICS_SCOPE_XPATH =
		"//span[text()='Analytics']/parent::a";

	public static final String AUTH_TOKEN_FIELD_XPATH =
		"//*[@id='_com_liferay_configuration_admin_web_portlet_InstanceS" +
			"ettingsPortlet_token']";

	public static final String CONNECT_AUTH_TOKEN_BUTTON_XPATH =
		"//button[@id='_com_liferay_configuration_admin_web_portlet_Instance" +
			"SettingsPortlet_tokenButton']";

	public static final String MASTER_OAUTH_SCOPES_TAB_XPATH =
		"//a[text()='Scopes']";

	public static final String OAUTH_ID_XPATH =
		"//input[@id='_com_liferay_oauth2_provider_web_internal_portlet_OAut" +
			"h2AdminPortlet_clientId']";

	public static final String OAUTH_SCOPES_TAB_XPATH =
		"//span[text()='Scopes']/parent::a";

	public static final String OAUTH_SECRET_XPATH =
		"//input[@id='_com_liferay_oauth2_provider_web_internal_portlet_OAut" +
			"h2AdminPortlet_clientSecret']";

	public static final String OAUTH_URL_PATH =
		"/group/control_panel/manage?p_p_id=com_liferay_oauth2_provider_web_" +
			"internal_portlet_OAuth2AdminPortlet";

	public static final String PAGE_CREATION_NAME_FIELD =
		"//input[@id='_com_liferay_layout_admin_web_portlet" +
			"_GroupPagesPortlet_name']";

	public static final String SCOPE_CHECKBOX_XPATH =
		"//a[@aria-expanded='true']//following-sibling::div//input[@type='c" +
			"heckbox']";

	public static final String SITES_CONTROL_PANEL_URL_PATH =
		"/group/control_panel/manage/-/sites/sites";

	public static final String SYNCED_CONTACTS_PATH =
		_createContactsControlPanelUrlPath();

	public static final String SYNCED_SITES_PATH =
		AC_INSTANCE_SETTINGS_URL_BASE + "1-synced-sites";

	public static final String USERS_ORGANIZATIONS_CONTROL_PANEL_URL_PATH =
		_createUsersOrganizationsControlPanelUrlPath();

	public static String getPageCreationUrlPath(String site) {
		StringBundler sb = new StringBundler(11);

		sb.append("/group/");
		sb.append(_lintNameForUrl(site));
		sb.append("/~/control_panel/manage?p_p_id=com_liferay_layout_adm");
		sb.append("in_web_portlet_GroupPagesPortlet&p_p_lifecycle=0&p_p_sta");
		sb.append("te=maximized&p_p_mode=view&_com_liferay_layout_admin_web");
		sb.append("_portlet_GroupPagesPortlet_mvcPath=%2Fselect_layout_page");
		sb.append("_template_entry.jsp&_com_liferay_layout_admin_web_portle");
		sb.append("t_GroupPagesPortlet_groupId=20126&p_r_p_selPlid=0&p_r_p_");
		sb.append("privateLayout=false&_com_liferay_layout_admin_web_portle");
		sb.append("t_GroupPagesPortlet_selectedTab=global-templates&p_p_aut");
		sb.append("h=em8IWLqf");

		return sb.toString();
	}

	public static String getSitePageUrl(String site, String page) {
		StringBundler sb = new StringBundler(4);

		sb.append("/web/");
		sb.append(_lintNameForUrl(site));
		sb.append(CharPool.FORWARD_SLASH);
		sb.append(_lintNameForUrl(page));

		return sb.toString();
	}

	private static String _createAcInstanceSettingsUrlBase() {
		StringBundler sb = new StringBundler(7);

		sb.append("/group/control_panel/manage?p_p_id=com_liferay_configurat");
		sb.append("ion_admin_web_portlet_InstanceSettingsPortlet&p_p_lifecyc");
		sb.append("le=0&p_p_mode=view&_com_liferay_configuration_admin_web_p");
		sb.append("ortlet_InstanceSettingsPortlet_mvcRenderCommandName=%2Fco");
		sb.append("nfiguration_admin%2Fview_configuration_screen&_com_lifera");
		sb.append("y_configuration_admin_web_portlet_InstanceSettingsPortlet");
		sb.append("_configurationScreenKey=");

		return sb.toString();
	}

	private static String _createContactsControlPanelUrlPath() {
		StringBundler sb = new StringBundler(9);

		sb.append("/group/control_panel/manage?p_p_id=com_liferay_configurat");
		sb.append("ion_admin_web_portlet_InstanceSettingsPortlet&p_p_lifecyc");
		sb.append("le=0&p_p_mode=view&_com_liferay_configuration_admin_web_p");
		sb.append("ortlet_InstanceSettingsPortlet_mvcRenderCommandName=%2Fan");
		sb.append("alytics_settings%2Fedit_synced_contacts&_com_liferay_conf");
		sb.append("iguration_admin_web_portlet_InstanceSettingsPortlet_confi");
		sb.append("gurationScreenKey%3D2-synced-contact-data&_com_liferay_co");
		sb.append("nfiguration_admin_web_portlet_InstanceSettingsPortlet_inc");
		sb.append("ludeSyncContactsFields=true");

		return sb.toString();
	}

	private static String _createUsersOrganizationsControlPanelUrlPath() {
		StringBundler sb = new StringBundler(3);

		sb.append("/group/control_panel/manage?p_p_id=com_liferay_users_");
		sb.append("admin_web_portlet_UsersAdminPortlet&p_p_lifecycle=0&p");
		sb.append("_p_state=maximized&p_v_l_s_g_id=20122");

		return sb.toString();
	}

	private static String _lintNameForUrl(String name) {
		if (name.equals("Liferay DXP")) {
			name = "guest";
		}
		else {
			name = name.trim();
			name = StringUtil.replace(name, CharPool.SPACE, CharPool.DASH);
		}

		return name;
	}

}