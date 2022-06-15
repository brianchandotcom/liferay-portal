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

package com.liferay.useraccount.extension;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.liferay.headless.admin.user.dto.v1_0.UserAccount;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLTypeExtension;
import com.liferay.portal.vulcan.graphql.contributor.GraphQLContributor;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Map;

/**
 * @author Javier de Arcos
 */
@Component(
		immediate = true,
		service = GraphQLContributor.class
)
public class UserAccountGraphQLContributor implements GraphQLContributor {

	@Activate
	public void activate() {
		UserAccountQueryContributor.setBiographyService(biographyService);
		UserAccountQueryContributor.setDtoConverterRegistry(_dtoConverterRegistry);
	}

	@Override
	public String getPath() {
		return "/headless-admin-user-graphql/v1_0";
	}

	@Override
	public UserAccountQueryContributor getQuery() {
		return new UserAccountQueryContributor();
	}

	public static class UserAccountQueryContributor {

		@GraphQLTypeExtension(UserAccount.class)
		public class UserAccountBiographyTypeExtension {

			public UserAccountBiographyTypeExtension(UserAccount userAccount) {
				this.userAccount = userAccount;
			}

			@GraphQLField
			public String biography() {
				return getGithubName(userAccount);
			}

			private final UserAccount userAccount;
		}

		@GraphQLTypeExtension(Object.class)
		public class UserAccountBiographyTypeExtension2<T> {

			public UserAccountBiographyTypeExtension2(T object) {
				this.object = object;
			}

			@GraphQLField
			public Map<String, Object> thisIsTheMap() {

//					for (String className : dtoConverter.getDTOClassNames()) {
//						try {
//							if (Class.forName(className).isInstance(object)) {
//								System.out.println("yes");
//							}
//						} catch (Exception exception) {
//							System.out.println(exception.getStackTrace());
//						}
//					}

				return HashMapBuilder
					.<String, Object>put("key1", "valueString")
					.<String, Object>put("key2", 2L)
					.<String, Object>put("key3", true)
					.build();
			}

			private final Object object;
		}

		private String getGithubName(UserAccount userAccount) {
			return biographyService.getBiography(userAccount.getAlternateName());
		}

		public static void setBiographyService(BiographyService service) {
			biographyService = service;
		}

		public static void setDtoConverterRegistry(DTOConverterRegistry dtoConverterRegistry) {
			dtoConverter = dtoConverterRegistry;
		}

		private static BiographyService biographyService;
		private static DTOConverterRegistry dtoConverter;
	}

	@Reference
	private BiographyService biographyService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;
}