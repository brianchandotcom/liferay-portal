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

import {useIsMounted} from 'frontend-js-react-web';
import PropTypes from 'prop-types';
import React from 'react';

import Hint from './Hint';

function TotalCount({
	className,
	dataProvider,
	label,
	popoverHeader,
	popoverMessage,
}) {
	const [value, setValue] = React.useState(null);
	const isMounted = useIsMounted();

	React.useEffect(() => {
		dataProvider()
			.then(value => {
				if (isMounted()) {
					setValue(value);
				}
			})
			.catch(() => {
				if (isMounted()) {
					setValue('-');
				}
			});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [dataProvider]);

	return (
		<div className={className}>
			<span className="text-secondary">
				{label}
				<Hint message={popoverMessage} title={popoverHeader} />
			</span>

			<span className="font-weight-bold">{value}</span>
		</div>
	);
}

TotalCount.propTypes = {
	dataProvider: PropTypes.func.isRequired,
	label: PropTypes.string.isRequired,
	popoverHeader: PropTypes.string.isRequired,
	popoverMessage: PropTypes.string.isRequired,
};

export default TotalCount;
