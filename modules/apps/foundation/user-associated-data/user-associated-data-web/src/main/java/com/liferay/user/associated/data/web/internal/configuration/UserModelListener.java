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

package com.liferay.user.associated.data.web.internal.configuration;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;

import java.io.IOException;

import java.util.Optional;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(immediate = true, service = ModelListener.class)
public class UserModelListener extends BaseModelListener<User> {

	@Override
	public void onAfterRemove(User user) throws ModelListenerException {
		try {
			_deleteAnonymousUserConfiguration(
				user.getCompanyId(), user.getUserId());
		}
		catch (InvalidSyntaxException | IOException e) {
			throw new ModelListenerException(e);
		}
	}

	private void _deleteAnonymousUserConfiguration(long companyId, long userId)
		throws InvalidSyntaxException, IOException {

		Optional<Configuration> configurationOptional =
			_anonymousUserConfigurationRetriever.getOptional(companyId, userId);

		if (!configurationOptional.isPresent()) {
			return;
		}

		Configuration configuration = configurationOptional.get();

		configuration.delete();
	}

	@Reference
	private AnonymousUserConfigurationRetriever
		_anonymousUserConfigurationRetriever;

}