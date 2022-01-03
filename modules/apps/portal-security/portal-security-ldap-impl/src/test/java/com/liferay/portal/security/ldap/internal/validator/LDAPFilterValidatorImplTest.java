/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.ldap.internal.validator;

import com.liferay.portal.security.ldap.SafeLdapFilterTemplate;
import com.liferay.portal.security.ldap.validator.LDAPFilterException;
import com.liferay.portal.security.ldap.validator.LDAPFilterValidator;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author James Lefeu
 * @author Vilmos Papp
 */
public class LDAPFilterValidatorImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testIsValidFilterBalancedParentheses() {
		Assert.assertTrue(_isValidFilter("(object=value)"));
		Assert.assertFalse(_isValidFilter("((((object=value))))"));
		Assert.assertFalse(_isValidFilter("((((object=value))(org=liferay)))"));
		Assert.assertFalse(
			_isValidFilter(
				"(((inetorg=www)((object=value))(org=liferay)))(user=test)"));
		Assert.assertFalse(_isValidFilter("(object=value))"));
		Assert.assertFalse(_isValidFilter("(((object=value))"));
		Assert.assertFalse(
			_isValidFilter("((((object=value)))(org=liferay)))"));
		Assert.assertFalse(
			_isValidFilter(
				"(((inetorg=www)((object=value))(org=liferay)))(user=test))"));
		Assert.assertTrue(_isValidFilter("(&(object=value)(org=liferay))"));
		Assert.assertTrue(_isValidFilter("(object=value)"));
		Assert.assertTrue(_isValidFilter("(object=value)"));
		Assert.assertTrue(_isValidFilter("(object=value=subvalue)"));
		Assert.assertTrue(_isValidFilter("(object<=value)"));
		Assert.assertTrue(_isValidFilter("(object<=value<=subvalue)"));
		Assert.assertTrue(_isValidFilter("(object>=value)"));
		Assert.assertTrue(_isValidFilter("(object>=value>=subvalue)"));
		Assert.assertTrue(_isValidFilter("(object~=value)"));
		Assert.assertTrue(_isValidFilter("(object~=value~=subvalue)"));
		Assert.assertTrue(
			_isValidFilter("(object~=value>=subvalue<=subsubvalue)"));
		Assert.assertTrue(_isValidFilter("(cn=Babs Jensen)"));
		Assert.assertTrue(_isValidFilter("(!(cn=Tim Howes))"));
		Assert.assertTrue(
			_isValidFilter(
				"(&(objectCategory=group)" +
					"(groupType:1.2.840.113556.1.4.803:=2147483648))"));
		Assert.assertTrue(
			_isValidFilter(
				"(memberof:1.2.840.113556.1.4.1941:=cn=Group1,OU=groupsOU," +
					"DC=x)"));
		Assert.assertTrue(
			_isValidFilter(
				"(&(objectCategory=person)(objectClass=contact)(|(sn=Smith)" +
					"(sn=Johnson)))"));
		Assert.assertTrue(
			_isValidFilter(
				"(userAccountControl:1.2.840.113556.1.4.804:=65568)"));
		Assert.assertTrue(
			_isValidFilter("(&(objectCategory=person)(objectClass=user))"));
		Assert.assertTrue(_isValidFilter("(sAMAccountType=805306368)"));
		Assert.assertTrue(_isValidFilter("(objectCategory=computer)"));
		Assert.assertTrue(_isValidFilter("(objectClass=contact)"));
		Assert.assertTrue(_isValidFilter("(objectCategory=group)"));
		Assert.assertTrue(
			_isValidFilter("(objectCategory=organizationalUnit)"));
		Assert.assertTrue(_isValidFilter("(objectCategory=container)"));
		Assert.assertTrue(_isValidFilter("(objectCategory=builtinDomain)"));
		Assert.assertTrue(_isValidFilter("(objectCategory=domain)"));
		Assert.assertTrue(_isValidFilter("(sAMAccountName>=x)"));
		Assert.assertTrue(
			_isValidFilter(
				"(userAccountControl:1.2.840.113556.1.4.803:=65536)"));
		Assert.assertTrue(
			_isValidFilter(
				"(&(objectCategory=person)(objectClass=user)" +
					"(userAccountControl:1.2.840.113556.1.4.803:=2))"));
		Assert.assertTrue(
			_isValidFilter(
				"(&(objectCategory=person)(objectClass=user)" +
					"(!(userAccountControl:1.2.840.113556.1.4.803:=2)))"));
		Assert.assertTrue(
			_isValidFilter(
				"(&(objectCategory=person)(objectClass=user)" +
					"(userAccountControl:1.2.840.113556.1.4.803:=32))"));
		Assert.assertTrue(
			_isValidFilter(
				"(&(objectCategory=person)(objectClass=user)" +
					"(userAccountControl:1.2.840.113556.1.4.803:=4194304))"));
		Assert.assertTrue(
			_isValidFilter(
				"(&(objectCategory=person)(objectClass=user)" +
					"(|(accountExpires=0)" +
						"(accountExpires=9223372036854775807)))"));
		Assert.assertTrue(
			_isValidFilter(
				"(&(objectCategory=person)(objectClass=user)" +
					"(accountExpires>=1)" +
						"(accountExpires<=9223372036854775806))"));
		Assert.assertTrue(
			_isValidFilter(
				"(userAccountControl:1.2.840.113556.1.4.803:=524288)"));
		Assert.assertTrue(
			_isValidFilter(
				"(userAccountControl:1.2.840.113556.1.4.803:=1048574)"));
		Assert.assertTrue(
			_isValidFilter(
				"(&(objectCategory=group)" +
					"(!(groupType:1.2.840.113556.1.4.803:=2147483648)))"));
		Assert.assertTrue(
			_isValidFilter("(groupType:1.2.840.113556.1.4.803:=2147483648)"));
		Assert.assertTrue(
			_isValidFilter("(groupType:1.2.840.113556.1.4.803:=1)"));
		Assert.assertTrue(
			_isValidFilter("(groupType:1.2.840.113556.1.4.803:=2)"));
		Assert.assertTrue(
			_isValidFilter("(groupType:1.2.840.113556.1.4.803:=4)"));
		Assert.assertTrue(
			_isValidFilter("(groupType:1.2.840.113556.1.4.803:=8)"));
		Assert.assertTrue(_isValidFilter("(groupType=-2147483646)"));
		Assert.assertTrue(_isValidFilter("(groupType=-2147483640)"));
		Assert.assertTrue(_isValidFilter("(groupType=-2147483644)"));
		Assert.assertTrue(_isValidFilter("(groupType=2)"));
		Assert.assertTrue(
			_isValidFilter(
				"(&(objectCategory=person)(objectClass=user)" +
					"(msNPAllowDialin=TRUE))"));
		Assert.assertTrue(
			_isValidFilter(
				"(&(objectCategory=group)(whenCreated>=20110301000000.0Z))"));
		Assert.assertTrue(
			_isValidFilter(
				"(&(objectCategory=person)(objectClass=user)(pwdLastSet=0))"));
		Assert.assertTrue(
			_isValidFilter(
				"(&(objectCategory=person)(objectClass=user)" +
					"(pwdLastSet>=129473172000000000))"));
		Assert.assertTrue(
			_isValidFilter(
				"(&(objectCategory=person)(objectClass=user)" +
					"(!(primaryGroupID=513)))"));
		Assert.assertTrue(
			_isValidFilter("(&(objectCategory=computer)(primaryGroupID=515))"));
		Assert.assertTrue(
			_isValidFilter("(objectGUID=90395F191AB51B4A9E9686C66CB18D11)"));
		Assert.assertTrue(
			_isValidFilter(
				"(objectSID=S-1-5-21-73586283-152049171-839522115-1111)"));
		Assert.assertTrue(
			_isValidFilter(
				"(objectSID=" +
					"0105000000000005150000006BD662041316100943170A325704" +
						"0000)"));
		Assert.assertTrue(
			_isValidFilter(
				"(&(objectCategory=computer)" +
					"(!(userAccountControl:1.2.840.113556.1.4.803:=8192)))"));
		Assert.assertTrue(
			_isValidFilter(
				"(&(objectCategory=computer)" +
					"(userAccountControl:1.2.840.113556.1.4.803:=8192))"));
		Assert.assertTrue(_isValidFilter("(primaryGroupID=516)"));
		Assert.assertTrue(
			_isValidFilter(
				"(!(userAccountControl:1.2.840.113556.1.4.803:=8192))"));
		Assert.assertTrue(
			_isValidFilter("(memberOf=cn=Test,ou=East,dc=Domain,dc=com)"));
		Assert.assertTrue(
			_isValidFilter(
				"(&(objectCategory=person)(objectClass=user)" +
					"(!(memberOf=cn=Test,ou=East,dc=Domain,dc=com)))"));
		Assert.assertTrue(
			_isValidFilter("(member=cn=Jim Smith,ou=West,dc=Domain,dc=com)"));
		Assert.assertTrue(
			_isValidFilter(
				"(memberOf:1.2.840.113556.1.4.1941:=cn=Test,ou=East," +
					"dc=Domain,dc=com)"));
		Assert.assertTrue(
			_isValidFilter(
				"(member:1.2.840.113556.1.4.1941:=cn=Jim Smith,ou=West," +
					"dc=Domain,dc=com)"));
		Assert.assertTrue(_isValidFilter("(anr=Jim Smith)"));
		Assert.assertTrue(
			_isValidFilter(
				"(&(objectCategory=attributeSchema)" +
					"(isMemberOfPartialAttributeSet=TRUE))"));
		Assert.assertTrue(
			_isValidFilter(
				"(&(objectCategory=attributeSchema)" +
					"(systemFlags:1.2.840.113556.1.4.803:=4))"));
		Assert.assertTrue(
			_isValidFilter(
				"(&(objectCategory=attributeSchema)" +
					"(systemFlags:1.2.840.113556.1.4.803:=1))"));
		Assert.assertTrue(
			_isValidFilter("(systemFlags:1.2.840.113556.1.4.803:=2147483648)"));
		Assert.assertTrue(
			_isValidFilter("(searchFlags:1.2.840.113556.1.4.803:=16)"));
		Assert.assertTrue(
			_isValidFilter("(searchFlags:1.2.840.113556.1.4.803:=8)"));
		Assert.assertTrue(
			_isValidFilter("(searchFlags:1.2.840.113556.1.4.803:=4)"));
		Assert.assertTrue(
			_isValidFilter("(searchFlags:1.2.840.113556.1.4.803:=1)"));
		Assert.assertTrue(
			_isValidFilter("(searchFlags:1.2.840.113556.1.4.803:=128)"));
		Assert.assertTrue(
			_isValidFilter("(searchFlags:1.2.840.113556.1.4.803:=512)"));
		Assert.assertTrue(_isValidFilter("(objectClass=siteLink)"));
		Assert.assertTrue(
			_isValidFilter(
				"(&(objectCategory=nTDSDSA)" +
					"(options:1.2.840.113556.1.4.803:=1))"));
		Assert.assertTrue(
			_isValidFilter("(objectCategory=msExchExchangeServer)"));
		Assert.assertTrue(_isValidFilter("(adminCount=1)"));
		Assert.assertTrue(_isValidFilter("(objectClass=trustedDomain)"));
		Assert.assertTrue(
			_isValidFilter("(objectCategory=groupPolicyContainer)"));
		Assert.assertTrue(
			_isValidFilter("(objectClass=serviceConnectionPoint)"));
		Assert.assertTrue(
			_isValidFilter(
				"(userAccountControl:1.2.840.113556.1.4.803:=67108864)"));
		Assert.assertTrue(
			_isValidFilter(
				"(objectCategory=cn=person,cn=Schema,cn=Configuration," +
					"dc=MyDomain,dc=com)"));
	}

	@Test
	public void testIsValidFilterNoFilterType() {
		Assert.assertTrue(_isValidFilter("(object=value)"));
		Assert.assertFalse(_isValidFilter("(object)"));
		Assert.assertFalse(_isValidFilter("(object)(value)"));
		Assert.assertFalse(_isValidFilter("(!object)"));
		Assert.assertFalse(_isValidFilter("(=value)"));
		Assert.assertFalse(_isValidFilter("(<=value)"));
		Assert.assertFalse(_isValidFilter("(>=value)"));
		Assert.assertFalse(_isValidFilter("(~=value)"));
		Assert.assertFalse(_isValidFilter("(~=value)(object=value)"));
	}

	@Test
	public void testIsValidFilterOpenAndCloseParentheses() {
		Assert.assertTrue(_isValidFilter("(object=value)"));
		Assert.assertFalse(_isValidFilter("(object=value)  "));
		Assert.assertFalse(_isValidFilter("  (object=value)"));
		Assert.assertFalse(_isValidFilter("((((object=value))))"));
		Assert.assertFalse(_isValidFilter("((((object=value))(org=liferay)))"));
		Assert.assertFalse(
			_isValidFilter(
				"(((inetorg=www)((object=value))(org=liferay)))(user=test)"));
		Assert.assertFalse(_isValidFilter("(object=value))"));
		Assert.assertFalse(_isValidFilter("(((object=value))"));
		Assert.assertFalse(
			_isValidFilter("((((object=value)))(org=liferay)))"));
		Assert.assertFalse(
			_isValidFilter(
				"(((inetorg=www)((object=value))(org=liferay)))(user=test))"));
		Assert.assertFalse(_isValidFilter("object=value)"));
		Assert.assertFalse(_isValidFilter("(object=value"));
		Assert.assertFalse(_isValidFilter("object=value"));
		Assert.assertFalse(_isValidFilter("(object=value)  "));
		Assert.assertFalse(_isValidFilter("("));
		Assert.assertFalse(_isValidFilter(")"));
		Assert.assertFalse(_isValidFilter(")("));
	}

	@Test
	public void testIsValidFilterSpecialChars() {
		Assert.assertTrue(_isValidFilter(""));
		Assert.assertFalse(_isValidFilter("*"));
		Assert.assertFalse(_isValidFilter("  *   "));
		Assert.assertTrue(_isValidFilter("(object=*)"));
		Assert.assertTrue(_isValidFilter("(object=subobject=*)"));
		Assert.assertTrue(_isValidFilter("(!(sAMAccountName=$*))"));
		Assert.assertTrue(
			_isValidFilter(
				"(&(objectClass=Person)(|(sn=Jensen)(cn=Babs J*)))"));
		Assert.assertTrue(_isValidFilter("(o=univ*of*mich*)"));
		Assert.assertTrue(_isValidFilter("(sn=sm*)"));
		Assert.assertTrue(
			_isValidFilter("(&(objectCategory=computer)(!(description=*)))"));
		Assert.assertTrue(
			_isValidFilter("(&(objectCategory=group)(description=*))"));
		Assert.assertTrue(
			_isValidFilter(
				"(&(objectCategory=person)(objectClass=user)(cn=Joe*))"));
		Assert.assertTrue(_isValidFilter("(telephoneNumber=*)"));
		Assert.assertTrue(
			_isValidFilter(
				"(&(objectCategory=group)(|(cn=Test*)(cn=Admin*)))"));
		Assert.assertTrue(
			_isValidFilter(
				"(&(objectCategory=person)(objectClass=user)(givenName=*)" +
					"(sn=*))"));
		Assert.assertTrue(
			_isValidFilter(
				"(&(objectCategory=person)(objectClass=user)(directReports=*)" +
					"(!(manager=*)))"));
		Assert.assertTrue(
			_isValidFilter(
				"(&(objectCategory=person)(objectClass=user)" +
					"(|(proxyAddresses=*:jsmith@company.com)" +
						"(mail=jsmith@company.com)))"));
		Assert.assertTrue(
			_isValidFilter("(description=East\\u005CWest Sales)"));
		Assert.assertTrue(_isValidFilter("(cn=Jim \\u002A Smith)"));
		Assert.assertTrue(
			_isValidFilter("(&(sAMAccountName<=a)(!(sAMAccountName=$*)))"));
		Assert.assertTrue(_isValidFilter("(servicePrincipalName=*)"));
		Assert.assertTrue(
			_isValidFilter(
				"(&(objectCategory=person)(objectClass=user)" +
					"(!(msNPAllowDialin=*)))"));
		Assert.assertTrue(_isValidFilter("(objectGUID=90395F191AB51B4A*)"));
		Assert.assertTrue(
			_isValidFilter(
				"(&(objectCategory=computer)(operatingSystem=*server*))"));
		Assert.assertTrue(
			_isValidFilter("(&(objectClass=domainDNS)(fSMORoleOwner=*))"));
		Assert.assertTrue(
			_isValidFilter("(&(objectClass=rIDManager)(fSMORoleOwner=*))"));
		Assert.assertTrue(
			_isValidFilter(
				"(&(objectClass=infrastructureUpdate)(fSMORoleOwner=*))"));
		Assert.assertTrue(
			_isValidFilter("(&(objectClass=dMD)(fSMORoleOwner=*))"));
		Assert.assertTrue(
			_isValidFilter(
				"(&(objectClass=crossRefContainer)(fSMORoleOwner=*))"));
	}

	@Test
	public void testIsValidFilterTypeAfterOpenParenthesis() {
		Assert.assertTrue(_isValidFilter("(object=value)"));
		Assert.assertFalse(_isValidFilter("(=value)"));
		Assert.assertFalse(_isValidFilter("(<=value)"));
		Assert.assertFalse(_isValidFilter("(>=value)"));
		Assert.assertFalse(_isValidFilter("(~=value)"));
		Assert.assertFalse(_isValidFilter("(~=value)(object=value)"));
	}

	@Test
	public void testIsValidFilterTypeBeforeCloseParenthesis() {
		Assert.assertTrue(_isValidFilter("(object=value)"));
		Assert.assertTrue(_isValidFilter("(object=*)"));
		Assert.assertTrue(_isValidFilter("(object=subobject=*)"));
	}

	@Test
	public void testIsValidFilterTypesInSequence() {
		Assert.assertTrue(_isValidFilter("(object=value)"));
		Assert.assertTrue(_isValidFilter("(object=value=subvalue)"));
		Assert.assertTrue(_isValidFilter("(object<=value)"));
		Assert.assertTrue(_isValidFilter("(object<=value<=subvalue)"));
		Assert.assertTrue(_isValidFilter("(object>=value)"));
		Assert.assertTrue(_isValidFilter("(object>=value>=subvalue)"));
		Assert.assertTrue(_isValidFilter("(object~=value)"));
		Assert.assertTrue(_isValidFilter("(object~=value~=subvalue)"));
		Assert.assertTrue(
			_isValidFilter("(object~=value>=subvalue<=subsubvalue)"));
		Assert.assertFalse(_isValidFilter("(object==value)"));
		Assert.assertFalse(_isValidFilter("(object=value=<=subvalue)"));
		Assert.assertFalse(_isValidFilter("(object~==value)"));
		Assert.assertFalse(_isValidFilter("(object=value=>=subvalue)"));
		Assert.assertFalse(
			_isValidFilter("(object~=value>==subvalue<=subsubvalue)"));
	}

	@Test
	public void testRFC4515() {
		Assert.assertTrue(_isValidFilter("(cn=Babs Jensen)"));
		Assert.assertTrue(_isValidFilter("(!(cn=Tim Howes))"));
		Assert.assertTrue(
			_isValidFilter(
				"(&(objectClass=Person)(|(sn=Jensen)(cn=Babs J*)))"));
		Assert.assertTrue(_isValidFilter("(o=univ*of*mich*)"));
		Assert.assertTrue(_isValidFilter("(seeAlso=)"));

		Assert.assertTrue(
			_isValidFilter("(cn:caseExactMatch:=Fred Flintstone)"));
		Assert.assertTrue(_isValidFilter("(cn:=Betty Rubble)"));
		Assert.assertTrue(_isValidFilter("(sn:dn:2.4.6.8.10:=Barney Rubble)"));
		Assert.assertTrue(_isValidFilter("(o:dn:=Ace Industry)"));
		Assert.assertTrue(_isValidFilter("(:1.2.3:=Wilma Flintstone)"));

		Assert.assertTrue(
			_isValidFilter(
				"(o=Parens R Us \\28for all your parenthetical needs\\29)"));

		Assert.assertTrue(_isValidFilter("(cn=*\\2A*)"));
		Assert.assertTrue(_isValidFilter("(filename=C:\\5cMyFile)"));
		Assert.assertTrue(_isValidFilter("(bin=\\00\\00\\00\\04)"));
		Assert.assertTrue(_isValidFilter("(sn=Lu\\c4\\8di\\c4\\87)"));
	}

	@Test
	public void testRFC4515UnsupportedFilters() {
		Assert.assertFalse(_isValidFilter("(:DN:2.4.6.8.10:=Dino)"));
		Assert.assertFalse(
			_isValidFilter("(1.3.6.1.4.1.1466.0=\\04\\02\\48\\69)"));
	}

	@Test
	public void testSafeLdapFilterTemplateValidations()
		throws LDAPFilterException {

		SafeLdapFilterTemplate safeLdapFilterTemplate =
			new SafeLdapFilterTemplate(
				"(mail=@email_address@)",
				new String[] {
					"@company_id@", "@email_address@", "@screen_name@",
					"@user_id@"
				},
				_ldapFilterValidator);

		safeLdapFilterTemplate = safeLdapFilterTemplate.replace(
			new String[] {"@email_address@"},
			new String[] {"test@liferay.com"});

		Assert.assertTrue(
			_isValidFilter(safeLdapFilterTemplate.getFilterString()));
	}

	private boolean _isValidFilter(String filter) {
		return _ldapFilterValidator.isValid(filter);
	}

	private static final LDAPFilterValidator _ldapFilterValidator =
		new LDAPFilterValidatorImpl();

}