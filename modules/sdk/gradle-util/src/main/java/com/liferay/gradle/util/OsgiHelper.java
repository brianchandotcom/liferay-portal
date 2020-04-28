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

    public static String getBundleSymbolicName(Project project) {
        BasePluginConvention basePluginConvention = GradleUtil.getConvention(
            project, BasePluginConvention.class);

        String archivesBaseName = basePluginConvention.getArchivesBaseName();

        Object group = project.getGroup();

        String groupId = group.toString();

        if (archivesBaseName.startsWith(groupId)) {
            return archivesBaseName;
        }
        String lastSection = groupId.substring(groupId.lastIndexOf('.') + 1);
        if (archivesBaseName.equals(lastSection)) {
            return groupId;
        }
        if (archivesBaseName.startsWith(lastSection)) {
            String artifactId = archivesBaseName.substring(
                lastSection.length());
            if (Character.isLetterOrDigit(artifactId.charAt(0))) {
                return _getBundleSymbolicName(groupId, artifactId);
            } else {
                return _getBundleSymbolicName(groupId, artifactId.substring(1));
            }
        }
        return _getBundleSymbolicName(groupId, archivesBaseName);
    }

    private static String _getBundleSymbolicName(
        String groupId, String artifactId) {

        return groupId + "." + artifactId;
    }

}