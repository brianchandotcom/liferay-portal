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

package com.liferay.portal.kernel.ldap;

import com.liferay.portal.security.ldap.LDAPFilterValidatorImpl;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author James Lefeu
 * @author Vilmos Papp
 */
public class LDAPUtilTest {

	@BeforeClass
	public static void setUpClass() {
		_ldapUtil = new LDAPUtil();
		_ldapUtil.setLDAPFilterValidator(new LDAPFilterValidatorImpl());
	}

	@Test
	public void testIsValidFilterBalancedParentheses() {
		Assert.assertTrue(isValidFilter("(object=value)"));
		Assert.assertFalse(isValidFilter("((((object=value))))"));
		Assert.assertFalse(isValidFilter("((((object=value))(org=liferay)))"));
		Assert.assertFalse(
			isValidFilter(
				"(((inetorg=www)((object=value))(org=liferay)))(user=test)"));
		Assert.assertFalse(isValidFilter("(object=value))"));
		Assert.assertFalse(isValidFilter("(((object=value))"));
		Assert.assertFalse(isValidFilter("((((object=value)))(org=liferay)))"));
		Assert.assertFalse(
			isValidFilter(
				"(((inetorg=www)((object=value))(org=liferay)))(user=test))"));
		Assert.assertTrue(isValidFilter("(&(object=value)(org=liferay))"));
		Assert.assertTrue(isValidFilter("(object=value)"));
		Assert.assertTrue(isValidFilter("(object=value)"));
		Assert.assertTrue(isValidFilter("(object=value=subvalue)"));
		Assert.assertTrue(isValidFilter("(object<=value)"));
		Assert.assertTrue(isValidFilter("(object<=value<=subvalue)"));
		Assert.assertTrue(isValidFilter("(object>=value)"));
		Assert.assertTrue(isValidFilter("(object>=value>=subvalue)"));
		Assert.assertTrue(isValidFilter("(object~=value)"));
		Assert.assertTrue(isValidFilter("(object~=value~=subvalue)"));
		Assert.assertTrue(
			isValidFilter("(object~=value>=subvalue<=subsubvalue)"));
		Assert.assertTrue(isValidFilter("(cn=Babs Jensen)"));
		Assert.assertTrue(isValidFilter("(!(cn=Tim Howes))"));
		Assert.assertTrue(
			isValidFilter(
				"(&(objectCategory=group)" +
				"(groupType:1.2.840.113556.1.4.803:=2147483648))"));
		Assert.assertTrue(
			isValidFilter(
				"(memberof:1.2.840.113556.1.4.1941:=cn=Group1,OU=groupsOU," +
				"DC=x)"));
		Assert.assertTrue(
			isValidFilter(
				"(&(objectCategory=person)(objectClass=contact)(|(sn=Smith)" +
				"(sn=Johnson)))"));
		Assert.assertTrue(
			isValidFilter(
				"(userAccountControl:1.2.840.113556.1.4.804:=65568)"));
		Assert.assertTrue(
			isValidFilter("(&(objectCategory=person)(objectClass=user))"));
		Assert.assertTrue(isValidFilter("(sAMAccountType=805306368)"));
		Assert.assertTrue(isValidFilter("(objectCategory=computer)"));
		Assert.assertTrue(isValidFilter("(objectClass=contact)"));
		Assert.assertTrue(isValidFilter("(objectCategory=group)"));
		Assert.assertTrue(isValidFilter("(objectCategory=organizationalUnit)"));
		Assert.assertTrue(isValidFilter("(objectCategory=container)"));
		Assert.assertTrue(isValidFilter("(objectCategory=builtinDomain)"));
		Assert.assertTrue(isValidFilter("(objectCategory=domain)"));
		Assert.assertTrue(isValidFilter("(sAMAccountName>=x)"));
		Assert.assertTrue(
			isValidFilter(
				"(userAccountControl:1.2.840.113556.1.4.803:=65536)"));
		Assert.assertTrue(
			isValidFilter(
				"(&(objectCategory=person)(objectClass=user)" +
				"(userAccountControl:1.2.840.113556.1.4.803:=2))"));
		Assert.assertTrue(
			isValidFilter(
				"(&(objectCategory=person)(objectClass=user)" +
				"(!(userAccountControl:1.2.840.113556.1.4.803:=2)))"));
		Assert.assertTrue(
			isValidFilter(
				"(&(objectCategory=person)(objectClass=user)" +
				"(userAccountControl:1.2.840.113556.1.4.803:=32))"));
		Assert.assertTrue(
			isValidFilter(
				"(&(objectCategory=person)(objectClass=user)" +
				"(userAccountControl:1.2.840.113556.1.4.803:=4194304))"));
		Assert.assertTrue(
			isValidFilter(
				"(&(objectCategory=person)(objectClass=user)" +
				"(|(accountExpires=0)(accountExpires=9223372036854775807)))"));
		Assert.assertTrue(
			isValidFilter(
				"(&(objectCategory=person)(objectClass=user)" +
				"(accountExpires>=1)(accountExpires<=9223372036854775806))"));
		Assert.assertTrue(
			isValidFilter(
				"(userAccountControl:1.2.840.113556.1.4.803:=524288)"));
		Assert.assertTrue(
			isValidFilter(
				"(userAccountControl:1.2.840.113556.1.4.803:=1048574)"));
		Assert.assertTrue(
			isValidFilter(
				"(&(objectCategory=group)" +
				"(!(groupType:1.2.840.113556.1.4.803:=2147483648)))"));
		Assert.assertTrue(
			isValidFilter("(groupType:1.2.840.113556.1.4.803:=2147483648)"));
		Assert.assertTrue(
			isValidFilter("(groupType:1.2.840.113556.1.4.803:=1)"));
		Assert.assertTrue(
			isValidFilter("(groupType:1.2.840.113556.1.4.803:=2)"));
		Assert.assertTrue(
			isValidFilter("(groupType:1.2.840.113556.1.4.803:=4)"));
		Assert.assertTrue(
			isValidFilter("(groupType:1.2.840.113556.1.4.803:=8)"));
		Assert.assertTrue(isValidFilter("(groupType=-2147483646)"));
		Assert.assertTrue(isValidFilter("(groupType=-2147483640)"));
		Assert.assertTrue(isValidFilter("(groupType=-2147483644)"));
		Assert.assertTrue(isValidFilter("(groupType=2)"));
		Assert.assertTrue(
			isValidFilter(
				"(&(objectCategory=person)(objectClass=user)" +
				"(msNPAllowDialin=TRUE))"));
		Assert.assertTrue(
			isValidFilter(
				"(&(objectCategory=group)(whenCreated>=20110301000000.0Z))"));
		Assert.assertTrue(
			isValidFilter(
				"(&(objectCategory=person)(objectClass=user)(pwdLastSet=0))"));
		Assert.assertTrue(
			isValidFilter(
				"(&(objectCategory=person)(objectClass=user)" +
				"(pwdLastSet>=129473172000000000))"));
		Assert.assertTrue(
			isValidFilter(
				"(&(objectCategory=person)(objectClass=user)" +
				"(!(primaryGroupID=513)))"));
		Assert.assertTrue(
			isValidFilter("(&(objectCategory=computer)(primaryGroupID=515))"));
		Assert.assertTrue(
			isValidFilter("(objectGUID=90395F191AB51B4A9E9686C66CB18D11)"));
		Assert.assertTrue(
			isValidFilter(
				"(objectSID=S-1-5-21-73586283-152049171-839522115-1111)"));
		Assert.assertTrue(
			isValidFilter(
				"(objectSID=" +
				"0105000000000005150000006BD662041316100943170A3257040000)"));
		Assert.assertTrue(
			isValidFilter(
				"(&(objectCategory=computer)" +
					"(!(userAccountControl:1.2.840.113556.1.4.803:=8192)))"));
		Assert.assertTrue(
			isValidFilter(
				"(&(objectCategory=computer)" +
				"(userAccountControl:1.2.840.113556.1.4.803:=8192))"));
		Assert.assertTrue(isValidFilter("(primaryGroupID=516)"));
		Assert.assertTrue(
			isValidFilter(
				"(!(userAccountControl:1.2.840.113556.1.4.803:=8192))"));
		Assert.assertTrue(
			isValidFilter("(memberOf=cn=Test,ou=East,dc=Domain,dc=com)"));
		Assert.assertTrue(
			isValidFilter(
				"(&(objectCategory=person)(objectClass=user)" +
				"(!(memberOf=cn=Test,ou=East,dc=Domain,dc=com)))"));
		Assert.assertTrue(
			isValidFilter("(member=cn=Jim Smith,ou=West,dc=Domain,dc=com)"));
		Assert.assertTrue(
			isValidFilter(
				"(memberOf:1.2.840.113556.1.4.1941:=cn=Test,ou=East," +
				"dc=Domain,dc=com)"));
		Assert.assertTrue(
			isValidFilter(
				"(member:1.2.840.113556.1.4.1941:=cn=Jim Smith,ou=West," +
				"dc=Domain,dc=com)"));
		Assert.assertTrue(isValidFilter("(anr=Jim Smith)"));
		Assert.assertTrue(
			isValidFilter(
				"(&(objectCategory=attributeSchema)" +
				"(isMemberOfPartialAttributeSet=TRUE))"));
		Assert.assertTrue(
			isValidFilter(
				"(&(objectCategory=attributeSchema)" +
				"(systemFlags:1.2.840.113556.1.4.803:=4))"));
		Assert.assertTrue(
			isValidFilter(
				"(&(objectCategory=attributeSchema)" +
				"(systemFlags:1.2.840.113556.1.4.803:=1))"));
		Assert.assertTrue(
			isValidFilter("(systemFlags:1.2.840.113556.1.4.803:=2147483648)"));
		Assert.assertTrue(
			isValidFilter("(searchFlags:1.2.840.113556.1.4.803:=16)"));
		Assert.assertTrue(
			isValidFilter("(searchFlags:1.2.840.113556.1.4.803:=8)"));
		Assert.assertTrue(
			isValidFilter("(searchFlags:1.2.840.113556.1.4.803:=4)"));
		Assert.assertTrue(
			isValidFilter("(searchFlags:1.2.840.113556.1.4.803:=1)"));
		Assert.assertTrue(
			isValidFilter("(searchFlags:1.2.840.113556.1.4.803:=128)"));
		Assert.assertTrue(
			isValidFilter("(searchFlags:1.2.840.113556.1.4.803:=512)"));
		Assert.assertTrue(isValidFilter("(objectClass=siteLink)"));
		Assert.assertTrue(
			isValidFilter(
				"(&(objectCategory=nTDSDSA)" +
				"(options:1.2.840.113556.1.4.803:=1))"));
		Assert.assertTrue(
			isValidFilter("(objectCategory=msExchExchangeServer)"));
		Assert.assertTrue(isValidFilter("(adminCount=1)"));
		Assert.assertTrue(isValidFilter("(objectClass=trustedDomain)"));
		Assert.assertTrue(
			isValidFilter("(objectCategory=groupPolicyContainer)"));
		Assert.assertTrue(
			isValidFilter("(objectClass=serviceConnectionPoint)"));
		Assert.assertTrue(
			isValidFilter(
				"(userAccountControl:1.2.840.113556.1.4.803:=67108864)"));
		Assert.assertTrue(
			isValidFilter(
				"(objectCategory=cn=person,cn=Schema,cn=Configuration," +
				"dc=MyDomain,dc=com)"));
	}

	@Test
	public void testIsValidFilterNoFilterType() {
		Assert.assertTrue(isValidFilter("(object=value)"));
		Assert.assertFalse(isValidFilter("(object)"));
		Assert.assertFalse(isValidFilter("(object)(value)"));
		Assert.assertFalse(isValidFilter("(!object)"));
		Assert.assertFalse(isValidFilter("(=value)"));
		Assert.assertFalse(isValidFilter("(<=value)"));
		Assert.assertFalse(isValidFilter("(>=value)"));
		Assert.assertFalse(isValidFilter("(~=value)"));
		Assert.assertFalse(isValidFilter("(~=value)(object=value)"));
		Assert.assertFalse(isValidFilter("(object=)"));
		Assert.assertFalse(isValidFilter("(object<=)"));
		Assert.assertFalse(isValidFilter("(object>=)"));
		Assert.assertFalse(isValidFilter("(object~=)"));
		Assert.assertFalse(isValidFilter("(object=subobject=)"));
		Assert.assertFalse(isValidFilter("(org=liferay)(object=subobject=)"));
	}

	@Test
	public void testIsValidFilterOpenAndCloseParentheses() {
		Assert.assertTrue(isValidFilter("(object=value)"));
		Assert.assertFalse(isValidFilter("(object=value)  "));
		Assert.assertFalse(isValidFilter("  (object=value)"));
		Assert.assertFalse(isValidFilter("((((object=value))))"));
		Assert.assertFalse(isValidFilter("((((object=value))(org=liferay)))"));
		Assert.assertFalse(
			isValidFilter(
				"(((inetorg=www)((object=value))(org=liferay)))(user=test)"));
		Assert.assertFalse(isValidFilter("(object=value))"));
		Assert.assertFalse(isValidFilter("(((object=value))"));
		Assert.assertFalse(isValidFilter("((((object=value)))(org=liferay)))"));
		Assert.assertFalse(
			isValidFilter(
				"(((inetorg=www)((object=value))(org=liferay)))(user=test))"));
		Assert.assertFalse(isValidFilter("object=value)"));
		Assert.assertFalse(isValidFilter("(object=value"));
		Assert.assertFalse(isValidFilter("object=value"));
		Assert.assertFalse(isValidFilter("(object=value)  "));
		Assert.assertFalse(isValidFilter("("));
		Assert.assertFalse(isValidFilter(")"));
		Assert.assertFalse(isValidFilter(")("));
	}

	@Test
	public void testIsValidFilterSpecialChars() {
		Assert.assertTrue(isValidFilter(""));
		Assert.assertFalse(isValidFilter("*"));
		Assert.assertFalse(isValidFilter("  *   "));
		Assert.assertTrue(isValidFilter("(object=*)"));
		Assert.assertTrue(isValidFilter("(object=subobject=*)"));
		Assert.assertTrue(isValidFilter("(!(sAMAccountName=$*))"));
		Assert.assertTrue(
			isValidFilter("(&(objectClass=Person)(|(sn=Jensen)(cn=Babs J*)))"));
		Assert.assertTrue(isValidFilter("(o=univ*of*mich*)"));
		Assert.assertTrue(isValidFilter("(sn=sm*)"));
		Assert.assertTrue(
			isValidFilter("(&(objectCategory=computer)(!(description=*)))"));
		Assert.assertTrue(
			isValidFilter("(&(objectCategory=group)(description=*))"));
		Assert.assertTrue(
			isValidFilter(
				"(&(objectCategory=person)(objectClass=user)(cn=Joe*))"));
		Assert.assertTrue(isValidFilter("(telephoneNumber=*)"));
		Assert.assertTrue(
			isValidFilter("(&(objectCategory=group)(|(cn=Test*)(cn=Admin*)))"));
		Assert.assertTrue(
			isValidFilter(
				"(&(objectCategory=person)(objectClass=user)(givenName=*)" +
				"(sn=*))"));
		Assert.assertTrue(
			isValidFilter(
				"(&(objectCategory=person)(objectClass=user)(directReports=*)" +
				"(!(manager=*)))"));
		Assert.assertTrue(
			isValidFilter(
				"(&(objectCategory=person)(objectClass=user)" +
				"(|(proxyAddresses=*:jsmith@company.com)" +
				"(mail=jsmith@company.com)))"));
		Assert.assertTrue(isValidFilter("(description=East\\u005CWest Sales)"));
		Assert.assertTrue(isValidFilter("(cn=Jim \\u002A Smith)"));
		Assert.assertTrue(
			isValidFilter("(&(sAMAccountName<=a)(!(sAMAccountName=$*)))"));
		Assert.assertTrue(isValidFilter("(servicePrincipalName=*)"));
		Assert.assertTrue(
			isValidFilter(
				"(&(objectCategory=person)(objectClass=user)" +
				"(!(msNPAllowDialin=*)))"));
		Assert.assertTrue(isValidFilter("(objectGUID=90395F191AB51B4A*)"));
		Assert.assertTrue(
			isValidFilter(
				"(&(objectCategory=computer)(operatingSystem=*server*))"));
		Assert.assertTrue(
			isValidFilter("(&(objectClass=domainDNS)(fSMORoleOwner=*))"));
		Assert.assertTrue(
			isValidFilter("(&(objectClass=rIDManager)(fSMORoleOwner=*))"));
		Assert.assertTrue(
			isValidFilter(
				"(&(objectClass=infrastructureUpdate)(fSMORoleOwner=*))"));
		Assert.assertTrue(
			isValidFilter("(&(objectClass=dMD)(fSMORoleOwner=*))"));
		Assert.assertTrue(
			isValidFilter(
				"(&(objectClass=crossRefContainer)(fSMORoleOwner=*))"));
	}

	@Test
	public void testIsValidFilterTypeAfterOpenParenthesis() {
		Assert.assertTrue(isValidFilter("(object=value)"));
		Assert.assertFalse(isValidFilter("(=value)"));
		Assert.assertFalse(isValidFilter("(<=value)"));
		Assert.assertFalse(isValidFilter("(>=value)"));
		Assert.assertFalse(isValidFilter("(~=value)"));
		Assert.assertFalse(isValidFilter("(~=value)(object=value)"));
	}

	@Test
	public void testIsValidFilterTypeBeforeCloseParenthesis() {
		Assert.assertTrue(isValidFilter("(object=value)"));
		Assert.assertTrue(isValidFilter("(object=*)"));
		Assert.assertTrue(isValidFilter("(object=subobject=*)"));
		Assert.assertFalse(isValidFilter("(object=)"));
		Assert.assertFalse(isValidFilter("(object<=)"));
		Assert.assertFalse(isValidFilter("(object>=)"));
		Assert.assertFalse(isValidFilter("(object~=)"));
		Assert.assertFalse(isValidFilter("(object=subobject=)"));
		Assert.assertFalse(isValidFilter("(org=liferay)(object=subobject=)"));
	}

	@Test
	public void testIsValidFilterTypesInSequence() {
		Assert.assertTrue(isValidFilter("(object=value)"));
		Assert.assertTrue(isValidFilter("(object=value=subvalue)"));
		Assert.assertTrue(isValidFilter("(object<=value)"));
		Assert.assertTrue(isValidFilter("(object<=value<=subvalue)"));
		Assert.assertTrue(isValidFilter("(object>=value)"));
		Assert.assertTrue(isValidFilter("(object>=value>=subvalue)"));
		Assert.assertTrue(isValidFilter("(object~=value)"));
		Assert.assertTrue(isValidFilter("(object~=value~=subvalue)"));
		Assert.assertTrue(
			isValidFilter("(object~=value>=subvalue<=subsubvalue)"));
		Assert.assertFalse(isValidFilter("(object==value)"));
		Assert.assertFalse(isValidFilter("(object=value=<=subvalue)"));
		Assert.assertFalse(isValidFilter("(object~==value)"));
		Assert.assertFalse(isValidFilter("(object=value=>=subvalue)"));
		Assert.assertFalse(
			isValidFilter("(object~=value>==subvalue<=subsubvalue)"));
	}

	protected boolean isValidFilter(String filter) {
		return _ldapUtil.isValidFilter(filter);
	}

	private static LDAPUtil _ldapUtil;

}