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

package com.liferay.osb.faro.service.impl;

import com.liferay.osb.faro.exception.EmailAddressDomainException;
import com.liferay.osb.faro.model.FaroProjectEmailAddressDomain;
import com.liferay.osb.faro.service.base.FaroProjectEmailAddressDomainLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Matthew Kong
 */
public class FaroProjectEmailAddressDomainLocalServiceImpl
	extends FaroProjectEmailAddressDomainLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public FaroProjectEmailAddressDomain addFaroProjectEmailAddressDomain(
		long groupId, long faroProjectId, String emailDomain) {

		long faroProjectEmailAddressDomainId = counterLocalService.increment();

		FaroProjectEmailAddressDomain faroProjectEmailAddressDomain =
			faroProjectEmailAddressDomainPersistence.create(
				faroProjectEmailAddressDomainId);

		faroProjectEmailAddressDomain.setGroupId(groupId);
		faroProjectEmailAddressDomain.setFaroProjectId(faroProjectId);
		faroProjectEmailAddressDomain.setEmailAddressDomain(emailDomain);

		return faroProjectEmailAddressDomainPersistence.update(
			faroProjectEmailAddressDomain);
	}

	@Override
	public void addFaroProjectEmailAddressDomains(
		long groupId, long faroProjectId, List<String> emailAddressDomains) {

		_validate(emailAddressDomains);

		faroProjectEmailAddressDomainPersistence.removeByFaroProjectId(
			faroProjectId);

		for (String emailAddressDomain : emailAddressDomains) {
			addFaroProjectEmailAddressDomain(
				groupId, faroProjectId, emailAddressDomain);
		}
	}

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();

		ClassLoader classLoader = getClassLoader();

		try (InputStream is = classLoader.getResourceAsStream(
				"com/liferay/osb/faro/dependencies" +
					"/email-domains-blacklist.csv")) {

			StringUtil.readLines(is, _emailAddressDomainsBlacklist);
		}
		catch (IOException ioException) {
			throw new SystemException(
				"Unable to read email domains blacklist", ioException);
		}
	}

	@Override
	public List<FaroProjectEmailAddressDomain>
		getFaroProjectEmailAddressDomainsByFaroProjectId(long faroProjectId) {

		return faroProjectEmailAddressDomainPersistence.findByFaroProjectId(
			faroProjectId);
	}

	@Override
	public List<FaroProjectEmailAddressDomain>
		getFaroProjectEmailAddressDomainsByGroupId(long groupId) {

		return faroProjectEmailAddressDomainPersistence.findByGroupId(groupId);
	}

	private void _validate(List<String> emailAddressDomains) {
		Set<String> invalidEmailAddressDomains = new HashSet<>(
			emailAddressDomains);

		invalidEmailAddressDomains.retainAll(_emailAddressDomainsBlacklist);

		if (!invalidEmailAddressDomains.isEmpty()) {
			throw new EmailAddressDomainException(
				"There are invalid email domains", invalidEmailAddressDomains);
		}
	}

	private final Set<String> _emailAddressDomainsBlacklist = new HashSet<>();

}