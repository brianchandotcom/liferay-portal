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

AUI.add(
	'liferay-staging-branch',
	(A) => {
		const Lang = A.Lang;

		const StagingBar = Liferay.StagingBar;

		A.mix(StagingBar, {
			_getBranchDialog() {
				const instance = this;

				let branchDialog = instance._branchDialog;

				if (!branchDialog) {
					const namespace = instance._namespace;

					branchDialog = Liferay.Util.Window.getWindow({
						dialog: {
							bodyContent: A.one(
								'#' + namespace + 'addBranch'
							).show(),
						},
						title: Liferay.Language.get('branch'),
					});

					instance._branchDialog = branchDialog;
				}

				return branchDialog;
			},

			addBranch(dialogTitle) {
				const instance = this;

				const branchDialog = instance._getBranchDialog();

				if (Lang.isValue(dialogTitle)) {
					branchDialog.set('title', dialogTitle);
				}

				branchDialog.show();
			},
		});
	},
	'',
	{
		requires: ['liferay-staging'],
	}
);
