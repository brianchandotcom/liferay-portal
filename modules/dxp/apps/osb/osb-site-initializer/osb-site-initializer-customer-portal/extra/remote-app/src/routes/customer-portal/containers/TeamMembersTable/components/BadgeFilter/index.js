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

const BadgeFilter = ({
	activationKeysLength,
	loading,
	filtersState: [filters],
}) => {
	return (
		<>
			<div className="d-flex">
				{!!filters.searchTerm && !loading && (
					<p className="font-weight-semi-bold m-0 mt-3 text-paragraph-sm">
						{activationKeysLength} {}
						result
						{activationKeysLength > 1 ? 's ' : ' '}
						for &quot;
						{filters.searchTerm}&quot;
					</p>
				)}
			</div>
		</>
	);
};

export default BadgeFilter;
