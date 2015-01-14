<#-- ---------- Common variables ---------- -->

<#assign theme_display = themeDisplay />
<#assign portlet_display = portletDisplay />

<#assign theme_timestamp = themeDisplay.getTheme().getTimestamp() />
<#assign theme_settings = themeDisplay.getThemeSettings() />

<#assign root_css_class = "aui " + languageUtil.get(locale, "lang.dir") />
<#assign css_class = htmlUtil.escape(theme_display.getColorScheme().getCssClass()) + " yui3-skin-sam" />

<#assign liferay_toggle_controls = sessionClicks.get(request, "liferay_toggle_controls", "visible") />

<#if layout??>
	<#assign page_group = layout.getGroup() />

	<#if page_group.isStagingGroup()>
		<#assign css_class = css_class + " staging local-staging" />
	<#elseif theme_display.isShowStagingIcon() && page_group.hasStagingGroup()>
		<#assign css_class = css_class + " live-view" />
	<#elseif theme_display.isShowStagingIcon() && page_group.isStagedRemotely()>
		<#assign css_class = css_class + " staging remote-staging" />
	</#if>

	<#if page_group.isControlPanel()>
		<#assign liferay_toggle_controls = "visible" />
	</#if>
</#if>

<#assign liferay_dockbar_pinned = getterUtil.getBoolean(sessionClicks.get(request, "liferay_dockbar_pinned", ""), false) />

<#if liferay_toggle_controls = "visible">
	<#assign css_class = css_class + " controls-visible" />
<#else>
	<#assign css_class = css_class + " controls-hidden" />
</#if>

<#if liferay_dockbar_pinned>
	<#assign css_class = css_class + " lfr-dockbar-pinned" />
</#if>

<#if layoutTypePortlet.hasStateMax()>
	<#assign css_class = css_class + " page-maximized" />
</#if>

<#assign css_folder = theme_display.getPathThemeCss() />
<#assign images_folder = theme_display.getPathThemeImages() />
<#assign javascript_folder = theme_display.getPathThemeJavaScript() />
<#assign templates_folder = theme_display.getPathThemeTemplates() />

<#assign full_css_path = fullCssPath />
<#assign full_templates_path = fullTemplatesPath />

<#assign css_main_file = htmlUtil.escape(portalUtil.getStaticResourceURL(request, "${css_folder}/main.css")) />
<#assign js_main_file = htmlUtil.escape(portalUtil.getStaticResourceURL(request, "${javascript_folder}/main.js")) />

<#assign company_id = company.getCompanyId() />
<#assign company_name = company.getName() />
<#assign company_logo = htmlUtil.escape(theme_display.getCompanyLogo()) />
<#assign company_logo_height = theme_display.getCompanyLogoHeight() />
<#assign company_logo_width = theme_display.getCompanyLogoWidth() />
<#assign company_url = theme_display.getURLHome() />

<#if !request.isRequestedSessionIdFromCookie()>
	<#assign company_url = portalUtil.getURLWithSessionId(company_url, request.getSession().getId()) />
</#if>

<#assign user_id = user.getUserId() />
<#assign is_default_user = user.isDefaultUser() />
<#assign user_first_name = user.getFirstName() />
<#assign user_middle_name = user.getMiddleName() />
<#assign user_last_name = user.getLastName() />
<#assign user_name = user.getFullName() />
<#assign is_male = user.isMale() />
<#assign is_female = user.isFemale() />
<#assign user_birthday = user.getBirthday() />
<#assign user_email_address = user.getEmailAddress() />
<#assign language = locale.getLanguage() />
<#assign language_id = user.getLanguageId() />
<#assign w3c_language_id = localeUtil.toW3cLanguageId(theme_display.getLanguageId()) />
<#assign time_zone = user.getTimeZoneId() />
<#assign user_greeting = htmlUtil.escape(user.getGreeting()) />
<#assign user_comments = user.getComments() />
<#assign user_login_ip = user.getLoginIP() />
<#assign user_last_login_ip = user.getLastLoginIP() />

<#assign is_login_redirect_required = portalUtil.isLoginRedirectRequired(request) />
<#assign is_signed_in = theme_display.isSignedIn() />

<#assign group_id = theme_display.getScopeGroupId() />

<#-- ---------- URLs ---------- -->

<#assign show_add_content = theme_display.isShowAddContentIcon() />

<#if show_add_content>
	<#assign add_content_text = languageUtil.get(locale, "add-application") />
	<#assign add_content_url = theme_display.getURLAddContent() />

	<#assign layout_text = languageUtil.get(locale, "layout-template") />
	<#assign layout_url = theme_display.getURLLayoutTemplates() />
</#if>

<#assign show_control_panel = theme_display.isShowControlPanelIcon() />

<#if show_control_panel>
	<#assign control_panel_text = languageUtil.get(locale, "control-panel") />
	<#assign control_panel_url = htmlUtil.escape(theme_display.getURLControlPanel()) />
</#if>

<#assign show_home = theme_display.isShowHomeIcon() />

<#if show_home>
	<#assign home_text = languageUtil.get(locale, "home") />
	<#assign home_url = htmlUtil.escape(theme_display.getURLHome()) />

	<#if !request.isRequestedSessionIdFromCookie()>
		<#assign home_url = htmlUtil.escape(portalUtil.getURLWithSessionId(home_url, request.getSession().getId())) />
	</#if>
</#if>

<#assign show_my_account = theme_display.isShowMyAccountIcon() />

<#if show_my_account>
	<#assign my_account_text = languageUtil.get(locale, "my-account") />
	<#if theme_display.getURLMyAccount()??>
		<#assign my_account_url = htmlUtil.escape(theme_display.getURLMyAccount().toString()) />
	</#if>
</#if>

<#assign show_page_settings = theme_display.isShowPageSettingsIcon() />
<#assign show_site_settings = theme_display.isShowSiteSettingsIcon() />

<#if show_page_settings>
	<#assign page_settings_text = languageUtil.get(locale, "manage-pages") />

	<#if theme_display.getURLPageSettings()??>
		<#assign page_settings_url = htmlUtil.escape(theme_display.getURLPageSettings().toString()) />
	</#if>
</#if>

<#assign show_sign_in = theme_display.isShowSignInIcon() />

<#if show_sign_in>
	<#assign sign_in_text = languageUtil.get(locale, "sign-in") />
	<#assign sign_in_url = htmlUtil.escape(theme_display.getURLSignIn()) />
</#if>

<#assign show_sign_out = theme_display.isShowSignOutIcon() />

<#if show_sign_out>
	<#assign sign_out_text = languageUtil.get(locale, "sign-out") />
	<#assign sign_out_url = htmlUtil.escape(theme_display.getURLSignOut()) />
</#if>

<#assign show_toggle_controls = theme_display.isSignedIn() />

<#if show_toggle_controls>
	<#assign toggle_controls_text = languageUtil.get(locale, "toggle-edit-controls") />
	<#assign toggle_controls_url = "javascript:;" />
</#if>

<#-- ---------- Page ---------- -->

<#assign the_title = "" />
<#assign selectable = theme_display.isTilesSelectable() />
<#assign is_maximized = layoutTypePortlet.hasStateMax() />
<#assign is_freeform = themeDisplay.isFreeformLayout() />

<#assign page_javascript_1 = "" />
<#assign page_javascript_2 = "" />
<#assign page_javascript_3 = "" />

<#if layout??>
	<#assign page = layout />

	<#assign is_first_child = page.isFirstChild() />
	<#assign is_first_parent = page.isFirstParent() />

	<#assign the_title = languageUtil.get(locale, the_title, page.getName(locale)) />

	<#assign is_portlet_page = false />

	<#if page.getType() = "portlet">
		<#assign is_portlet_page = true />
	</#if>

	<#if is_portlet_page && theme_display.isWapTheme()>
		<#assign all_portlets = layoutTypePortlet.getPortlets() />
		<#assign column_1_portlets = layoutTypePortlet.getAllPortlets("column-1") />
		<#assign column_2_portlets = layoutTypePortlet.getAllPortlets("column-2") />
		<#assign column_3_portlets = layoutTypePortlet.getAllPortlets("column-3") />
		<#assign column_4_portlets = layoutTypePortlet.getAllPortlets("column-4") />
		<#assign column_5_portlets = layoutTypePortlet.getAllPortlets("column-5") />

		<#if layoutTypePortlet.hasStateMax()>
			<#assign maximized_portlet_id = layoutTypePortlet.getStateMaxPortletId() />
		</#if>
	</#if>

	<#assign typeSettingsProperties = layout.getTypeSettingsProperties() />

	<#if typeSettingsProperties??>
		<#assign page_javascript = typeSettingsProperties["javascript"]! />
	</#if>

	<#assign site_name = htmlUtil.escape(page_group.getDescriptiveName()) />

	<#assign community_name = site_name />

	<#assign is_guest_group = page_group.getName() == "Guest" />

	<#if is_guest_group>
		<#assign css_class = css_class + " guest-site" />
	</#if>

	<#if is_signed_in>
		<#assign css_class = css_class + " signed-in" />
	<#else>
		<#assign css_class = css_class + " signed-out" />
	</#if>

	<#if layout.isPublicLayout()>
		<#assign css_class = css_class + " public-page" />
	<#else>
		<#assign css_class = css_class + " private-page" />
	</#if>

	<#if page_group.isLayoutPrototype()>
		<#assign css_class = css_class + " page-template" />
	</#if>

	<#if page_group.isLayoutSetPrototype()>
		<#assign css_class = css_class + " site-template" />
	</#if>

	<#if page_group.isCompany()>
		<#assign site_type = "company-site" />
	<#elseif page_group.isOrganization()>
		<#assign site_type = "organization-site" />
	<#elseif page_group.isUser()>
		<#assign site_type = "user-site" />
	<#else>
		<#assign site_type = "site" />
	</#if>

	<#assign css_class = css_class + " " + site_type />

	<#assign my_sites_portlet_url = portletURLFactory.create(request, "49", page.getPlid(), "ACTION_PHASE") />

	<#assign my_places_portlet_url = my_sites_portlet_url />

	${my_sites_portlet_url.setWindowState("normal")}
	${my_sites_portlet_url.setPortletMode("view")}

	${my_sites_portlet_url.setParameter("struts_action", "/my_sites/view")}
	${my_sites_portlet_url.setParameter("groupId", "${page.getGroupId()}")}
	${my_sites_portlet_url.setParameter("privateLayout", "false")}

	<#assign site_default_public_url = htmlUtil.escape(my_sites_portlet_url.toString()) />

	<#assign community_default_public_url = site_default_public_url />

	${my_sites_portlet_url.setParameter("privateLayout", "true")}

	<#assign site_default_private_url = htmlUtil.escape(my_sites_portlet_url.toString()) />

	<#assign community_default_private_url = site_default_private_url />

	<#assign site_default_url = site_default_public_url />

	<#assign community_default_url = site_default_url />

	<#if layout.isPrivateLayout()>
		<#assign site_default_url = site_default_private_url />

		<#assign community_default_url = site_default_url />
	</#if>
</#if>

<#assign the_title = "" />

<#if layout.getHTMLTitle(locale)??>
	<#assign the_title = layout.getHTMLTitle(locale) />
</#if>

<#if pageTitle??>
	<#assign the_title = pageTitle />
</#if>

<#if pageSubtitle??>
	<#assign the_title = pageSubtitle + " - " + the_title />
</#if>

<#if tilesTitle != "">
	<#assign the_title = languageUtil.get(locale, tilesTitle) />
</#if>

<#if page_group.isLayoutPrototype()>
	<#assign the_title = page_group.getDescriptiveName() />
</#if>

<#if tilesTitle == "">
	<#assign the_title = htmlUtil.escape(the_title) />
</#if>

<#if layouts??>
	<#assign pages = layouts />
</#if>

<#-- ---------- Logo ---------- -->

<#assign logo_css_class = "logo" />
<#assign use_company_logo = !layout.layoutSet.isLogo() />
<#assign site_logo_height = company_logo_height />
<#assign site_logo_width = company_logo_width />

<#if company.getLogoId() == 0 && use_company_logo>
	<#assign logo_css_class = logo_css_class + " default-logo" />
<#else>
	<#assign logo_css_class = logo_css_class + " custom-logo" />
</#if>

<#assign show_site_name_supported = getterUtil.getBoolean(theme_settings["show-site-name-supported"]!"", true) />

<#assign show_site_name_default = getterUtil.getBoolean(theme_settings["show-site-name-default"]!"", show_site_name_supported) />

<#assign show_site_name = getterUtil.getBoolean(layout.layoutSet.getSettingsProperty("showSiteName"), show_site_name_default) />

<#assign site_logo = company_logo />
<#assign logo_description = htmlUtil.escape(site_name) />

<#-- ---------- Navigation ---------- -->

<#assign has_navigation = false />

<#if navItems??>
	<#assign nav_items = navItems />
	<#assign has_navigation = (nav_items?size > 0) />
</#if>

<#assign nav_css_class = "sort-pages modify-pages" />

<#if !has_navigation>
	<#assign nav_css_class = nav_css_class + " hide" />
</#if>

<#-- ---------- Staging ---------- -->

<#assign show_staging = theme_display.isShowStagingIcon() />

<#if show_staging>
	<#assign staging_text = languageUtil.get(locale, "staging") />
</#if>

<#-- ---------- My sites ---------- -->

<#assign show_my_sites = user.hasMySites() />

<#assign show_my_places = show_my_sites />

<#if show_my_sites>
	<#assign my_sites_text = languageUtil.get(locale, "my-sites") />

	<#assign my_places_text = my_sites_text />
</#if>

<#-- ---------- Includes ---------- -->

<#if is_portlet_page && theme_display.isWapTheme()>
	<#assign dir_include = "/wap" />
<#else>
	<#assign dir_include = "/html" />
</#if>

<#assign body_bottom_include = "${dir_include}/common/themes/body_bottom.jsp" />
<#assign body_top_include = "${dir_include}/common/themes/body_top.jsp" />
<#assign bottom_include = "${dir_include}/common/themes/bottom.jsp" />
<#assign bottom_ext_include = bottom_include />
<#assign content_include = "${dir_include}${tilesContent}" />
<#assign top_head_include = "${dir_include}/common/themes/top_head.jsp" />
<#assign top_messages_include = "${dir_include}/common/themes/top_messages.jsp" />

<#-- ---------- Date ---------- -->

<#assign date = dateUtil />
<#assign current_time = date.newDate() />
<#assign the_year = current_time?date?string("yyyy") />

<#-- ---------- Custom init ---------- -->

<#include "${full_templates_path}/init_custom.ftl" />