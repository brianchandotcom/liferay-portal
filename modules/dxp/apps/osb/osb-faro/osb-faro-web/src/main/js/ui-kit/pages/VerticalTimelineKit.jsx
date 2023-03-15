import React from 'react';
import Row from '../components/Row';
import VerticalTimeline from 'shared/components/VerticalTimelineDeprecated';
import {Link} from 'react-router-dom';

const NESTED_ITEMS = [
	{
		subtitle: 'www.liferay.com/testing',
		symbol: 'web-content',
		time: 1518648993917,
		title: 'Visited Liferay: Testing'
	},
	{
		subtitle:
			'www.liferay.com/testing/testing/testing/testing/testing/testing/testing/testing/testing/testing/testing/testing/testing/testing/testing/testing/testing/testing/testing/testing/testing/testing/testing/testing/testing/testing/testing/testing/testing/testing/ 2',
		symbol: 'web-content',
		time: 1518648993917,
		title: 'Visited Liferay: Testing 2'
	},
	{
		subtitle: 'www.liferay.com/testing 3',
		symbol: 'web-content',
		time: 1518648993917,
		title:
			'Visited Liferay: Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat'
	}
];

const ITEMS = [
	{
		header: true,
		title: 'Aug 19, 2021'
	},
	{
		subtitle: [
			<div key='subtitle1'>
				<span>{'1 Download'}</span>
				<span>{'3 Visits'}</span>
			</div>
		],
		time: 1518648993917,
		title: [
			'Visited',
			' ',
			<Link key='3' to='#1'>
				{'www.liferay.com'}
			</Link>
		],
		type: 'Document'
	},
	{
		nestedItems: NESTED_ITEMS,
		subtitle: '10 Downloads',
		time: 1518648993917,
		title: [
			<Link key='1' to='#2'>
				{'New Business Purchase'}
			</Link>
		],
		type: 'Download'
	},
	{
		header: true,
		title: 'Yesterday'
	},
	{
		nestedItems: NESTED_ITEMS,
		subtitle: '2 Submissions',
		symbol: 'web-content',
		time: 1518648993917,
		title: ['Opened Email'],
		type: 'Download'
	}
];

export default class VerticalTimelineKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					<VerticalTimeline
						headerLabels={{
							count: Liferay.Language.get('activity-count'),
							label: Liferay.Language.get('time'),
							title: Liferay.Language.get('session')
						}}
						items={ITEMS}
					/>
				</Row>

				<Row>
					<VerticalTimeline
						headerLabels={{
							count: Liferay.Language.get('activity-count'),
							label: Liferay.Language.get('time'),
							title: Liferay.Language.get('session')
						}}
						initialExpanded={false}
						items={ITEMS}
					/>
				</Row>
			</div>
		);
	}
}
