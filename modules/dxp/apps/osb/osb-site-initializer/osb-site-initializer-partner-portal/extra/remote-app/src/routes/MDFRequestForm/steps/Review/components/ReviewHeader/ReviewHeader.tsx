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

interface IProps {
	description?: string;
	name?: string;
	title?: string;
}

const ReviewBod = ({description, name, title}: IProps) => (
	<div className="border border-light mt-4 pt-3 px-6 rounded">
		<div>
			<div className="font-weight-bold mb-1 text-primary">
				{name && <div> {name.toUpperCase()}</div>}
			</div>

			<h5 className="h3 mb-2">{title}</h5>

			{description && (
				<div className="mb-3 text-neutral-8">{description}</div>
			)}
		</div>
	</div>
);

export default ReviewBod;
