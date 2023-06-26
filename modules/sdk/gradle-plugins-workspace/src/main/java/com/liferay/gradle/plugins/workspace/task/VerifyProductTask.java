/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.workspace.task;

import java.util.Objects;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;

import com.liferay.gradle.plugins.workspace.WorkspaceExtension;
import com.liferay.gradle.plugins.workspace.WorkspaceExtension.ProductInfo;
import com.liferay.gradle.util.Validator;

/**
 * @author Simon Jiang
 */
public class VerifyProductTask extends DefaultTask {

	@Input
	@Optional
	public String getProcut() {
		return _product;
	}

	@Input
	@Optional
	public WorkspaceExtension getExtension() {
		return _extension;
	}

	public void setProduct(String product) {
		_product = product;
	}

	public void setExtension(WorkspaceExtension extension) {
		_extension = extension;
	}

	@TaskAction
	public void verifyProduct() throws Exception {
		ProductInfo productInfo = _extension.getProductInfo();
		
		if (Objects.isNull(productInfo)) {
			throw new GradleException(
				"Can not get produtInfo for product '" + _product + "'");
		}

		String appServerTomcatVersion = _extension.getAppServerTomcatVersion();
		String defaultAppServerTomcatVersion = productInfo.getAppServerTomcatVersion();
		
		if (Objects.equals(appServerTomcatVersion, defaultAppServerTomcatVersion) 
				&& Validator.isNull(defaultAppServerTomcatVersion)) {
			throw new GradleException(
				"Can not get correct tomcat version for product '" + _product +
					"'");
		}

		String bundleChecksumMD5 = _extension.getBundleChecksumMD5();
		String defaultBundleChecksumMD5 = productInfo.getBundleChecksumMD5();
		
		if (Objects.equals(bundleChecksumMD5, defaultBundleChecksumMD5) 
				&& Validator.isNull(defaultBundleChecksumMD5)) {
			throw new GradleException(
				"Can not get correct bundleChecksumMD5 for product '" +
					_product + "'");
		}

		String bundleUrl = _extension.getBundleUrl();
		String defaultBundleUrl = productInfo.getBundleUrl();		
		
		if (Objects.equals(bundleUrl, defaultBundleUrl) 
				&& Validator.isNull(defaultBundleUrl)) {
			throw new GradleException(
				"Can not get correct bundle url for product '" + _product +
					"'");
		}

		String dockerImage = _extension.getDockerImageLiferay();
		String defaultLiferayDockerImage = productInfo.getLiferayDockerImage();	
		
		if (Objects.equals(dockerImage, defaultLiferayDockerImage) 
				&& Validator.isNull(defaultLiferayDockerImage)) {
			throw new GradleException(
				"Can not get correct liferay docker image for product '" +
					_product + "'");
		}

		String targetPlatformVersion = _extension.getTargetPlatformVersion();
		String defaultTargetPlatformVersion = productInfo.getTargetPlatformVersion();			
		
		if (Objects.equals(targetPlatformVersion, defaultTargetPlatformVersion) 
				&& Validator.isNull(defaultTargetPlatformVersion)) {
			throw new GradleException(
				"Can not get correct tareget platform version for product '" +
					_product + "'");
		}
	}

	private String _product;
	private WorkspaceExtension _extension;

}