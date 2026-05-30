import * as API from 'shared/api';
import React, {useEffect, useState} from 'react';
import {matchPath, useLocation, useNavigate} from 'react-router-dom';
import {Routes, toRoute} from 'shared/util/router';
import {WrapSafeResults} from 'shared/hoc/util';

interface IWrappedComponentProps {
	groupId: string;
}

const checkSegmentLink =
	(WrappedComponent: React.ComponentType<IWrappedComponentProps>) =>
	({
		groupId,
		...otherProps
	}: IWrappedComponentProps & {[key: string]: any}) => {
		const location = useLocation();
		const navigate = useNavigate();

		const [error, setError] = useState();
		const [loading, setLoading] = useState(false);

		useEffect(() => {
			const segment = matchPath<'channelId' | 'id', string>(
				{
					end: true,
					path: Routes.CONTACTS_SEGMENT
				},
				location.pathname
			);

			if (segment && !segment.params.channelId) {
				setLoading(true);

				API.individualSegment
					.fetch({groupId, segmentId: segment.params.id})
					.then(({channelId, id}) => {
						setLoading(false);

						navigate(
							toRoute(Routes.CONTACTS_SEGMENT, {
								channelId,
								groupId,
								id
							}),
							{replace: true}
						);
					})
					.catch(err => {
						setLoading(false);
						setError(err);
					});
			}
		}, []);

		return (
			<WrapSafeResults error={error} loading={loading} page pageDisplay>
				<WrappedComponent {...otherProps} groupId={groupId} />
			</WrapSafeResults>
		);
	};

export default checkSegmentLink;
