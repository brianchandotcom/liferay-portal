/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {Button} from '../../../../common/components';

const ManageProductUser = ({
	activationStatusAC,
	activationStatusDXPC,
	refLinkAC,
	refLinkDXPC,
}) => {
	return (
		<div className="bg-brand-primary-lighten-6 border-0 card card-flat cp-manager-product-container mt-5">
			<div className="p-4">
				<p className="h4">Manage Product Users</p>

				<p className="mt-2 text-neutral-7 text-paragraph-sm">
					Manage roles and permissions of users within each product.
				</p>

				<div className="d-flex">
					{activationStatusDXPC && (
						<a
							href={refLinkDXPC}
							rel="noopener noreferrer"
							target="_blank"
						>
							<Button
								appendIcon="shortcut"
								className="align-items-stretch btn btn-ghost cp-manager-product-button d-flex mr-3 p-2 text-neutral-10"
							>
								<p className="font-weight-semi-bold h6 m-0 pl-1">
									Manage DXP Cloud Users
								</p>
							</Button>
						</a>
					)}

					{activationStatusAC && (
						<a
							href={refLinkAC}
							rel="noopener noreferrer"
							target="_blank"
						>
							<Button
								appendIcon="shortcut"
								className="align-items-stretch btn btn-ghost cp-manager-product-button d-flex p-2 text-neutral-10"
							>
								<p className="font-weight-semi-bold h6 m-0 pl-1">
									Manage Analytics Cloud Users
								</p>
							</Button>
						</a>
					)}
				</div>
			</div>
		</div>
	);
};
export default ManageProductUser;
