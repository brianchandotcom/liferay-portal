import Card from 'shared/components/Card';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import InterestPagesList from 'contacts/components/InterestPagesList';
import Nav from 'shared/components/Nav';
import React from 'react';
import {EntityTypes} from 'shared/util/constants';
import {Individual} from 'shared/util/records';
import {PropTypes} from 'prop-types';
import {Routes, toRoute} from 'shared/util/router';
import {setUriQueryValue} from 'shared/util/router';
import {sub} from 'shared/util/lang';

export default class InterestDetails extends React.Component {
	static defaultProps = {
		active: 'true'
	};

	static propTypes = {
		active: PropTypes.string,
		channelId: PropTypes.string,
		groupId: PropTypes.string.isRequired,
		id: PropTypes.string.isRequired,
		individual: PropTypes.instanceOf(Individual).isRequired,
		interestId: PropTypes.string.isRequired
	};

	getNavigationItems() {
		const {channelId, groupId, id, interestId} = this.props;

		const active = this.props.active === 'true';

		return [
			{
				active,
				href: setUriQueryValue(
					toRoute(Routes.CONTACTS_INDIVIDUAL_INTEREST_DETAILS, {
						channelId,
						groupId,
						id,
						interestId
					}),
					'active',
					true
				),
				label: Liferay.Language.get('active-pages')
			},
			{
				active: !active,
				href: setUriQueryValue(
					toRoute(Routes.CONTACTS_INDIVIDUAL_INTEREST_DETAILS, {
						channelId,
						groupId,
						id,
						interestId
					}),
					'active',
					false
				),
				label: Liferay.Language.get('inactive-pages')
			}
		];
	}

	render() {
		const {
			props: {channelId, groupId, id, interestId}
		} = this;

		const interestName = decodeURIComponent(interestId);

		return (
			<>
				<div className='back-button-root mb-2'>
					<ClayLink
						borderless
						button
						displayType='secondary'
						href={toRoute(Routes.CONTACTS_INDIVIDUAL_INTERESTS, {
							channelId,
							groupId,
							id
						})}
					>
						<ClayIcon
							className='icon-root mr-2'
							symbol='angle-left'
						/>

						{Liferay.Language.get('back-to-interests')}
					</ClayLink>
				</div>

				<Card pageDisplay>
					<Card.Header>
						<Card.Title>
							{sub(
								Liferay.Language.get('pages-containing-x'),
								[
									<span
										className='interest-name'
										key='INTEREST_NAME'
									>
										{`"${interestName}"`}
									</span>
								],
								false
							)}
						</Card.Title>
					</Card.Header>

					<Card.Header>
						<Nav
							className='page-subnav'
							display='underline'
							key='subnav'
						>
							{this.getNavigationItems().map(
								({active, href, label}) => (
									<Nav.Item
										active={active}
										href={href}
										key={label}
									>
										<h4>{label}</h4>
									</Nav.Item>
								)
							)}
						</Nav>
					</Card.Header>

					<InterestPagesList
						channelId={channelId}
						className='interest-history-table'
						dataSourceParams={{
							active: this.props.active === 'true',
							contactsEntityId: id,
							contactsEntityType: EntityTypes.Individual,
							groupId,
							interestName
						}}
						groupId={groupId}
					/>
				</Card>
			</>
		);
	}
}
