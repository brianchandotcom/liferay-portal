import Avatar from 'shared/components/Avatar';
import Card from 'shared/components/Card';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import getCN from 'classnames';
import React from 'react';
import {formatDateToTimeZone} from 'shared/util/date';
import {Individual} from 'shared/util/records';
import {PropTypes} from 'prop-types';
import {Routes, toRoute} from 'shared/util/router';
import {sub} from 'shared/util/lang';

export default class DetailsCard extends React.PureComponent {
	static defaultProps = {
		entity: new Individual()
	};

	static propTypes = {
		channelId: PropTypes.string,
		entity: PropTypes.instanceOf(Individual),
		groupId: PropTypes.string.isRequired,
		timeZoneId: PropTypes.string.isRequired
	};

	render() {
		const {channelId, className, entity, groupId, timeZoneId} = this.props;

		const individual = entity.toJS();

		const {
			dateCreated,
			id,
			name,
			properties: {email, jobTitle}
		} = individual;

		return (
			<Card className={getCN('individual-details-card-root', className)}>
				<Card.Body>
					<Avatar circle entity={individual} size='xl' />

					{name && <h4>{name}</h4>}

					{jobTitle && <div className='job-title'>{jobTitle}</div>}

					{email && <div className='email'>{email}</div>}

					{dateCreated && (
						<div className='first-seen'>
							{sub(Liferay.Language.get('first-seen-x'), [
								formatDateToTimeZone(
									dateCreated,
									'LL',
									timeZoneId
								)
							])}
						</div>
					)}
				</Card.Body>

				<Card.Footer>
					<ClayLink
						borderless
						button
						className='button-root'
						displayType='secondary'
						href={toRoute(Routes.CONTACTS_INDIVIDUAL_DETAILS, {
							channelId,
							groupId,
							id
						})}
						small
					>
						{Liferay.Language.get('view-all-details')}

						<ClayIcon
							className='icon-root ml-2'
							symbol='angle-right-small'
						/>
					</ClayLink>
				</Card.Footer>
			</Card>
		);
	}
}
