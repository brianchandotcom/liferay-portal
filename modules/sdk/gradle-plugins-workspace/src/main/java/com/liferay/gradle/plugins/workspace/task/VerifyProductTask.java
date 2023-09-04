/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.workspace.task;

import com.liferay.gradle.plugins.workspace.WorkspaceExtension;
import com.liferay.gradle.util.Validator;

import java.util.Objects;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Simon Jiang
 */
public class VerifyProductTask extends DefaultTask {

	@Input
	@Optional
	public WorkspaceExtension getExtension() {
		return _extension;
	}

	@Input
	@Optional
	public String getProduct() {
		return _product;
	}

	public void setExtension(WorkspaceExtension extension) {
		_extension = extension;
	}

	public void setProduct(String product) {
		_product = product;
	}

	@TaskAction
	public void verifyProduct() throws Exception {
		WorkspaceExtension.ProductInfo productInfo =
			_extension.getProductInfo();

		if (Objects.isNull(productInfo)) {
			throw new GradleException(
				"Unable to get product info for product '" + _product + "'");
		}

		if (Validator.isNull(_extension.getAppServerTomcatVersion()) &&
			Validator.isNull(productInfo.getAppServerTomcatVersion())) {

			throw new GradleException(
				"Unable to get Tomcat version for product '" + _product + "'");
		}

		if (Validator.isNull(_extension.getBundleChecksumMD5()) &&
			Validator.isNull(productInfo.getBundleChecksumMD5())) {

			throw new GradleException(
				"Unable to get bundle checksum MD5 for product '" + _product +
					"'");
		}

		if (Validator.isNull(_extension.getBundleUrl()) &&
			Validator.isNull(productInfo.getBundleUrl())) {

			throw new GradleException(
				"Unable to get bundle URL for product '" + _product + "'");
		}

		if (Validator.isNull(_extension.getDockerImageLiferay()) &&
			Validator.isNull(productInfo.getLiferayDockerImage())) {

			throw new GradleException(
				"Unable to get Liferay Docker image for product '" + _product +
					"'");
		}

		if (Validator.isNull(_extension.getTargetPlatformVersion()) &&
			Validator.isNull(productInfo.getTargetPlatformVersion())) {

			throw new GradleException(
				"Unable to get target platform version for product '" +
					_product + "'");
		}
	}

	private WorkspaceExtension _extension;
	private String _product;

}