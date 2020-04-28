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

package com.liferay.gradle.util;

import org.gradle.api.Project;
import org.gradle.api.plugins.BasePluginConvention;

public class OsgiHelper {

    public String getBundleSymbolicName(Project project) {
        String group = project.getGroup().toString();

        BasePluginConvention basePluginConvention = GradleUtil.getConvention(
            project, BasePluginConvention.class);

        String archivesBaseName = basePluginConvention.getArchivesBaseName();
        if (archivesBaseName.startsWith(group)) {
            return archivesBaseName;
        }
        int i = group.lastIndexOf('.');
        String lastSection = group.substring(++i);
        if (archivesBaseName.equals(lastSection)) {
            return group;
        }
        if (archivesBaseName.startsWith(lastSection)) {
            String artifactId = archivesBaseName.substring(
                lastSection.length());
            if (Character.isLetterOrDigit(artifactId.charAt(0))) {
                return getBundleSymbolicName(group, artifactId);
            } else {
                return getBundleSymbolicName(group, artifactId.substring(1));
            }
        }
        return getBundleSymbolicName(group, archivesBaseName);
    }

    private String getBundleSymbolicName(String groupId, String artifactId) {
        return groupId + "." + artifactId;
    }

}