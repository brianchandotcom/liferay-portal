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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.osgi.service.component.annotations.Component;

import java.io.IOException;
import java.net.URL;

/**
 * @author Javier de Arcos
 */
@Component(
        immediate = true,
        service = BiographyService.class
)
public class GithubBiographyService implements BiographyService {

    private static final String GITHUB_USERS_ENDPOINT = "https://api.github.com/users/";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public String getBiography(String userId) {
        try {
            if (userId != null) {
                URL url = new URL(GITHUB_USERS_ENDPOINT + userId);
                GithubUser githubUser = OBJECT_MAPPER.readValue(url, GithubUser.class);
                return githubUser.getBio();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static class GithubUser {
        private String login;
        private String bio;

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }
    }
}
