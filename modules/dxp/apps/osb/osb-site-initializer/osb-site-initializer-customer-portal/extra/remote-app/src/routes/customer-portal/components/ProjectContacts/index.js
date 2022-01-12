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

import ProjectContactsSkeleton from './Skeleton';

const ProjectContacts = ({contact}) => {
	return (
		<div className="container mb-5 mx-0 project-contacs-container">
			<div className="row">
				<div className="col-5"></div>

				<div className="col-7">
					<h5 className="mb-4 rounded-sm text-neutral-10">
						Liferay Contact
					</h5>

					{contact.name && (
						<div className="font-weight-bold rounded-sm text-neutral-8 text-paragraph">
							{contact.name}
						</div>
					)}

					{contact.role && (
						<div className="rounded-sm text-neutral-10 text-paragraph">
							{contact.role}
						</div>
					)}

					{contact.email && (
						<div className="rounded-sm text-neutral-10 text-paragraph-sm">
							{contact.email}
						</div>
					)}
				</div>
			</div>
		</div>
	);
};

ProjectContacts.Skeleton = ProjectContactsSkeleton;

export default ProjectContacts;
