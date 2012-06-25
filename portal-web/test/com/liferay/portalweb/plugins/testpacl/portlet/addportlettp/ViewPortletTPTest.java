/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portalweb.plugins.testpacl.portlet.addportlettp;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPortletTPTest extends BaseTestCase {
	public void testViewPortletTP() throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Test PACL Test Page",
			RuntimeVariables.replace("Test PACL Test Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isVisible("//section"));
		assertEquals(RuntimeVariables.replace("Test PACL"),
			selenium.getText("//h1[@class='portlet-title']/span"));
		assertFalse(selenium.isTextPresent("FAILED"));
		assertEquals(RuntimeVariables.replace("Bean Property"),
			selenium.getText("xPath=(//h1[@class='header-title']/span)[1]"));
		assertEquals(RuntimeVariables.replace("Get"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[1]"));
		assertEquals(RuntimeVariables.replace(
				"JournalContentUtil#getJournalContent= PASSED\n LanguageUtil#getLanguage= PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[1]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("Set"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[2]"));
		assertEquals(RuntimeVariables.replace(
				"EntityCacheUtil#setEntityCache= PASSED\n FinderCacheUtil#setFinderCache= PASSED\n PortalCustomSQLUtil#setPortalCustomSQL= PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[2]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("Class Loader"),
			selenium.getText("xPath=(//h1[@class='header-title']/span)[2]"));
		assertEquals(RuntimeVariables.replace("Get"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[3]"));
		assertEquals(RuntimeVariables.replace(
				"com.liferay.chat.model.EntryClp#toEscapedModel= PASSED\n com.liferay.chat.model.EntryClp.class#getClassLoader= PASSED\n com.liferay.chat.service.EntryLocalService#getClass#getClassLoader= PASSED\n com.liferay.portal.kernel.portlet.PortletClassLoaderUtil#getClassLoader(\"1_WAR_chatportlet\")= PASSED\n com.liferay.portal.kernel.portlet.PortletClassLoaderUtil#getClassLoader(\"1_WAR_flashportlet\")= PASSED\n com.liferay.portal.kernel.portlet.PortletClassLoaderUtil#getClassLoader(\"chat-portlet\")= PASSED\n com.liferay.portal.kernel.portlet.PortletClassLoaderUtil#getClassLoader(\"flash-portlet\")= PASSED\n com.liferay.portal.kernel.util.PortalClassLoaderUtil#getClassLoader= PASSED\n com.liferay.portal.util.Portal#getClass#getClassLoader= PASSED\n com.liferay.portlet.blogs.service.BlogsEntryLocalService#getClass#getClassLoader= PASSED\n com.liferay.testpacl.service.FooLocalService#getClass#getClassLoader= PASSED\n com.liferay.testpacl.util.TestPACLUtil.class#getClassLoader= PASSED\n java.lang.ClassLoader#getSystemClassLoader= PASSED\n java.lang.Class#getClassLoader= PASSED\n java.lang.Object.class#getClassLoader= PASSED\n java.lang.Object#getClass#getClassLoader= PASSED\n java.lang.Thread#getContextClassLoader= PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[3]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("Set"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[4]"));
		assertEquals(RuntimeVariables.replace("java.net.URLClassLoader= PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[4]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("Dynamic Query"),
			selenium.getText("xPath=(//h1[@class='header-title']/span)[3]"));
		assertEquals(RuntimeVariables.replace("Chat Portlet"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[5]"));
		assertEquals(RuntimeVariables.replace(
				"EntryLocalServiceUtil#dynamicQuery= PASSED\n StatusLocalServiceUtil#dynamicQuery= PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[5]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("Portal"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[6]"));
		assertEquals(RuntimeVariables.replace(
				"DynamicQueryFactoryUtil#forClass(Group.class)= PASSED\n DynamicQueryFactoryUtil#forClass(Role.class)= PASSED\n GroupLocalServiceUtil#dynamicQuery= PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[6]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("Test PACL Portlet"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[7]"));
		assertEquals(RuntimeVariables.replace(
				"DynamicQueryFactoryUtil#forClass(Foo.class)= PASSED\n FooLocalServiceUtil#dynamicQuery= PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[7]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("Expando Bridge"),
			selenium.getText("xPath=(//h1[@class='header-title']/span)[4]"));
		assertEquals(RuntimeVariables.replace(
				"Group#getExpandoBridge= PASSED\n Group#setExpandoBridgeAttributes= PASSED\n GroupWrapper#getExpandoBridge= PASSED\n GroupWrapper#setExpandoBridgeAttributes= PASSED\n User#getExpandoBridge= PASSED\n User#setExpandoBridgeAttributes= PASSED\n UserWrapper#getExpandoBridge= PASSED\n UserWrapper#setExpandoBridgeAttributes= PASSED"),
			selenium.getText(
				"xPath=(//h1[@class='header-title']/span)[4]/ancestor::div[contains(@class,'taglib-header')]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("Files"),
			selenium.getText("xPath=(//h1[@class='header-title']/span)[5]"));
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[8]"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='portlet-body']/h3)[8]/following-sibling::p",
				"liferay-releng.properties=PASSED"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='portlet-body']/h3)[8]/following-sibling::p",
				"ChatUtil.java=PASSED"));
		assertEquals(RuntimeVariables.replace("Execute"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[9]"));
		assertEquals(RuntimeVariables.replace(
				"../webapps/chat-portlet/images/saved.png=PASSED\nping.exe=PASSED\nC:\\WINDOWS\\system32\\ping.exe=PASSED\nC:\\WINDOWS\\system32\\whoami.exe=PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[9]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("Read"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[10]"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='portlet-body']/h3)[10]/following-sibling::p",
				"main.css=PASSED"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='portlet-body']/h3)[10]/following-sibling::p",
				"saved.png=PASSED"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='portlet-body']/h3)[10]/following-sibling::p",
				"web.xml=PASSED"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='portlet-body']/h3)[10]/following-sibling::p",
				"Language.properties=PASSED"));
		assertEquals(RuntimeVariables.replace("Write"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[11]"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='portlet-body']/h3)[11]/following-sibling::p",
				"main.css=PASSED"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='portlet-body']/h3)[11]/following-sibling::p",
				"saved.png=PASSED"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='portlet-body']/h3)[11]/following-sibling::p",
				"liferay-releng.properties=PASSED"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='portlet-body']/h3)[11]/following-sibling::p",
				"Language.properties=PASSED"));
		assertEquals(RuntimeVariables.replace("Hook"),
			selenium.getText("xPath=(//h1[@class='header-title']/span)[6]"));
		assertEquals(RuntimeVariables.replace("Indexers"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[12]"));
		assertEquals(RuntimeVariables.replace(
				"Organization=PASSED\n User=PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[12]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("Language.properties"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[13]"));
		assertEquals(RuntimeVariables.replace(
				"en_UK=PASSED\n en_US=PASSED\n es_ES=PASSED\n it_IT=PASSED\n pt_BR=PASSED\n pt_PT=PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[13]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("portal.properties"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[14]"));
		assertEquals(RuntimeVariables.replace(
				"locales.beta=PASSED\n phone.number.format.impl=PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[14]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("Services"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[15]"));
		assertEquals(RuntimeVariables.replace(
				"BlogsEntryLocalService#getBlogsEntriesCount=PASSED\n BlogsStatsUserLocalService#getBlogsStatsUsersCount=PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[15]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("Struts Actions"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[16]"));
		assertEquals(RuntimeVariables.replace(
				"/portal/test/pacl/failure=PASSED\n /portal/test/pacl/success=PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[16]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("JNDI"),
			selenium.getText("xPath=(//h1[@class='header-title']/span)[7]"));
		assertEquals(RuntimeVariables.replace("Bind"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[17]"));
		assertEquals(RuntimeVariables.replace(
				"test-pacl-matthew=PASSED\ntest-pacl-Matthew=PASSED\ntest-pacl-mark=PASSED\ntest-pacl-Mark=PASSED\ntest-pacl-luke=PASSED\ntest-pacl-Luke=PASSED\ntest-pacl-john 3:16=PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[17]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("Lookup"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[18]"));
		assertTrue(selenium.isPartialText("//section/div/div/div",
				"Lookup java_liferay:jdbc/LiferayPool=PASSED\ntest-pacl-matthew=PASSED\ntest-pacl-matthew=PASSED\ntest-pacl-mark=PASSED\ntest-pacl-Mark=PASSED\ntest-pacl-luke=PASSED\ntest-pacl-Luke=PASSED\ntest-pacl-john 3:16=PASSED"));
		assertEquals(RuntimeVariables.replace("Unbind"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[19]"));
		assertEquals(RuntimeVariables.replace(
				"test-pacl-matthew=PASSED\ntest-pacl-Matthew=PASSED\ntest-pacl-mark=PASSED\ntest-pacl-Mark=PASSED\ntest-pacl-luke=PASSED\ntest-pacl-Luke=PASSED\ntest-pacl-john 3:16=PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[19]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("Message Bus"),
			selenium.getText("xPath=(//h1[@class='header-title']/span)[8]"));
		assertEquals(RuntimeVariables.replace("Listen"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[20]"));
		assertEquals(RuntimeVariables.replace(
				"liferay/test_pacl_listen_failure= PASSED\n liferay/test_pacl_listen_success= PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[20]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("Send"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[21]"));
		assertEquals(RuntimeVariables.replace(
				"liferay/test_pacl_send_failure= PASSED\n liferay/test_pacl_send_success= PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[21]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("Reflection"),
			selenium.getText("xPath=(//h1[@class='header-title']/span)[9]"));
		assertEquals(RuntimeVariables.replace("Portal"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[22]"));
		assertEquals(RuntimeVariables.replace(
				"PropsKeys.class#ADMIN_DEFAULT_GROUP_NAMES= PASSED\n PropsKeys.class#ADMIN_DEFAULT_GROUP_NAMES#setAccessible(false)= PASSED\n UserLocalServiceUtil.class#_service= PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[22]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("Test PACL Portlet"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[23]"));
		assertEquals(RuntimeVariables.replace(
				"TestPACLUtil.class#TEST_FIELD= PASSED\n TestPACLUtil.class#TEST_FIELD#setAccessible(false)= PASSED\n TestPACLUtil.class#_log= PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[23]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("Search Engine"),
			selenium.getText("xPath=(//h1[@class='header-title']/span)[10]"));
		assertEquals(RuntimeVariables.replace(
				"GENERIC_ENGINE= PASSED\n SYSTEM_ENGINE= PASSED"),
			selenium.getText(
				"xPath=(//h1[@class='header-title']/span)[10]/ancestor::div[contains(@class,'taglib-header')]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("Services: Chat Portlet"),
			selenium.getText("xPath=(//h1[@class='header-title']/span)[11]"));
		assertEquals(RuntimeVariables.replace(
				"com.liferay.chat.service.impl.EntryLocalServiceImpl"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[24]"));
		assertEquals(RuntimeVariables.replace(
				"EntryLocalServiceUtil#getEntry= PASSED\n EntryLocalServiceUtil#getEntries= PASSED\n EntryLocalServiceUtil#updateEntry= PASSED\n FooLocalServiceUtil#getEntryLocalServiceUtil_GetEntry= PASSED\n FooLocalServiceUtil#getEntryLocalServiceUtil_GetEntries= PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[24]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace(
				"com.liferay.chat.service.impl.StatusLocalServiceImpl"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[25]"));
		assertEquals(RuntimeVariables.replace(
				"FooLocalServiceUtil#getStatusLocalServiceUtil_GetStatus= PASSED\n FooLocalServiceUtil#getStatusLocalServiceUtil_GetStatuses= PASSED\n StatusLocalServiceUtil#getStatus= PASSED\n StatusLocalServiceUtil#getStatuses= PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[25]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("Services: Portal"),
			selenium.getText("xPath=(//h1[@class='header-title']/span)[12]"));
		assertEquals(RuntimeVariables.replace(
				"com.liferay.portal.service.impl.CompanyLocalServiceImpl"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[26]"));
		assertEquals(RuntimeVariables.replace(
				"CompanyLocalServiceUtil#getCompany= PASSED\n FooLocalServiceUtil#getCompanyPersistence_FindByPrimaryKey= PASSED\n FooLocalServiceUtil#getCompanyUtil_FindByPrimaryKey= PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[26]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace(
				"com.liferay.portal.service.impl.GroupLocalServiceImpl"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[27]"));
		assertEquals(RuntimeVariables.replace(
				"GroupLocalServiceUtil#getGroup= PASSED\n FooLocalServiceUtil#getGroupPersistence_FindByPrimaryKey= PASSED\n FooLocalServiceUtil#getGroupUtil_FindByPrimaryKey= PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[27]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace(
				"com.liferay.portal.service.impl.PortalServiceImpl"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[28]"));
		assertEquals(RuntimeVariables.replace(
				"FooLocalServiceUtil#getPortalService_GetBuildNumber= PASSED\n FooLocalServiceUtil#getPortalService_TestGetBuildNumber= PASSED\n FooLocalServiceUtil#getPortalService_TestHasClassName= PASSED\n FooLocalServiceUtil#getPortalServiceUtil_GetBuildNumber= PASSED\n FooLocalServiceUtil#getPortalServiceUtil_TestGetBuildNumber= PASSED\n FooLocalServiceUtil#getPortalServiceUtil_TestHasClassName= PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[28]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace(
				"com.liferay.portal.service.impl.UserLocalServiceImpl"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[29]"));
		assertEquals(RuntimeVariables.replace(
				"FooLocalServiceUtil#getUserPersistence_FindByPrimaryKey= PASSED\n FooLocalServiceUtil#getUserUtil_FindByPrimaryKey= PASSED\n UserLocalServiceUtil#getUser= PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[29]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("Services: Test PACL Portlet"),
			selenium.getText("xPath=(//h1[@class='header-title']/span)[13]"));
		assertEquals(RuntimeVariables.replace(
				"com.liferay.testpacl.service.impl.FooLocalServiceImpl"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[30]"));
		assertEquals(RuntimeVariables.replace(
				"FooLocalServiceUtil#getFoosCount= PASSED\n FooLocalServiceUtil#getReleaseInfo_GetBuildNumber= PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[30]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("Sockets"),
			selenium.getText("xPath=(//h1[@class='header-title']/span)[14]"));
		assertEquals(RuntimeVariables.replace("Accept"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[31]"));
		assertEquals(RuntimeVariables.replace(
				"localhost:4320= PASSED\n localhost:4321= PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[31]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("Connect"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[32]"));
		assertEquals(RuntimeVariables.replace(
				"www.abc.com:80= PASSED\n www.cbs.com:80= PASSED\n www.msn.com:80= PASSED\n www.google.com:80= PASSED\n www.google.com:443= PASSED\n www.yahoo.com:80= PASSED\n www.yahoo.com:443= PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[32]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("Listen"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[33]"));
		assertEquals(RuntimeVariables.replace(
				"4315= PASSED\n 4316= PASSED\n 4317= PASSED\n 4318= PASSED\n 4319= PASSED\n 4320= PASSED\n 4321= PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[33]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("SQL"),
			selenium.getText("xPath=(//h1[@class='header-title']/span)[15]"));
		assertEquals(RuntimeVariables.replace("Create"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[34]"));
		assertEquals(RuntimeVariables.replace(
				"create table TestPACL_CreateFailure (userId bigint)=PASSED\ncreate table TestPACL_CreateFailure (userId bigint)=PASSED\ncreate table TestPACL_CreateSuccess (userId bigint)=PASSED\ncreate table TestPACL_CreateSuccess (userId bigint)=PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[34]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("Drop"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[35]"));
		assertEquals(RuntimeVariables.replace(
				"drop table TestPACL_DropFailure=PASSED\ndrop table TestPACL_DropFailure=PASSED\ndrop table TestPACL_DropSuccess=PASSED\ndrop table TestPACL_DropSuccess=PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[35]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("Insert"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[36]"));
		assertEquals(RuntimeVariables.replace(
				"insert into TestPACL_InsertFailure values (1)=PASSED\ninsert into TestPACL_InsertFailure values (1)=PASSED\ninsert into TestPACL_InsertSuccess values (1)=PASSED\ninsert into TestPACL_InsertSuccess values (1)=PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[36]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("Replace"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[37]"));
		assertEquals(RuntimeVariables.replace(
				"replace TestPACL_ReplaceFailure (userId) values (1)=PASSED\nreplace TestPACL_ReplaceFailure (userId) values (1)=PASSED\nreplace TestPACL_ReplaceSuccess (userId) values (1)=PASSED\nreplace TestPACL_ReplaceSuccess (userId) values (1)=PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[37]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("Select"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[38]"));
		assertEquals(RuntimeVariables.replace(
				"select * from Counter=PASSED\nselect * from Counter=PASSED\nselect * from Counter inner join User_ on User_.userId = Counter.currentId=PASSED\nselect * from Counter inner join User_ on User_.userId = Counter.currentId=PASSED\nselect * from TestPACL_Bar=PASSED\nselect * from TestPACL_Bar=PASSED\nselect * from User_=PASSED\nselect * from User_=PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[38]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("Truncate"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[39]"));
		assertEquals(RuntimeVariables.replace(
				"truncate table TestPACL_TruncateFailure=PASSED\ntruncate table TestPACL_TruncateFailure=PASSED\ntruncate table TestPACL_TruncateSuccess=PASSED\ntruncate table TestPACL_TruncateSuccess=PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[39]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("Update"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[40]"));
		assertEquals(RuntimeVariables.replace(
				"update ListType set name = 'Test PACL' where listTypeId = -123=PASSED\nupdate ListType set name = 'Test PACL' where listTypeId = -123=PASSED\nupdate ListType set name = 'Test PACL' where listTypeId = -123=PASSED\nupdate ListType set name = 'Test PACL' where listTypeId = (select userId from User_)=PASSED\nupdate ListType set name = 'Test PACL' where listTypeId = (select userId from User_)=PASSED\nupdate ListType set name = 'Test PACL' where listTypeId = (select userId from User_)=PASSED\nupdate User_ set firstName = 'Test PACL' where userId = -123=PASSED\nupdate User_ set firstName = 'Test PACL' where userId = -123=PASSED\nupdate User_ set firstName = 'Test PACL' where userId = -123=PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[40]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("Threads"),
			selenium.getText("xPath=(//h1[@class='header-title']/span)[16]"));
		assertEquals(RuntimeVariables.replace("Current Thread"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[41]"));
		assertEquals(RuntimeVariables.replace(
				"PortalServiceUtil#getBuildNumber=PASSED\n UserLocalServiceUtil#getUser=PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[41]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("Message Bus Thread"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[42]"));
		assertEquals(RuntimeVariables.replace(
				"PortalServiceUtil#getBuildNumber=PASSED\n UserLocalServiceUtil#getUser=PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[42]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("New Thread"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[43]"));
		assertEquals(RuntimeVariables.replace(
				"PortalServiceUtil#getBuildNumber=PASSED\n UserLocalServiceUtil#getUser=PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[43]/following-sibling::p"));
		assertEquals(RuntimeVariables.replace("Portal Executor Manager"),
			selenium.getText("xPath=(//div[@class='portlet-body']/h3)[44]"));
		assertEquals(RuntimeVariables.replace(
				"PortalExecutorManagerUtil.shutdown(\"liferay/hot_deploy\")= PASSED"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/h3)[44]/following-sibling::p"));
	}
}