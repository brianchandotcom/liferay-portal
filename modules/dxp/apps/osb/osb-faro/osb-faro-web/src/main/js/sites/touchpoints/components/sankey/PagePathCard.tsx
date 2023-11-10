import Card from 'shared/components/Card';
import ErrorDisplay from 'shared/components/ErrorDisplay';
import PagePathQuery from 'shared/queries/PagePathQuery';
import React from 'react';
import Sankey from './Sankey';
import StatesRenderer from 'shared/components/states-renderer/StatesRenderer';
import {getSafeRangeSelectors} from 'shared/util/util';
import {SECONDARY_NODE_COLOR} from './utils';
import {TitleKey, Type} from './types';
import {useParams} from 'react-router-dom';
import {useQuery} from '@apollo/react-hooks';
import {v4 as uuidv4} from 'uuid';

type pagePathNode = {
	views: number;
	canonicalUrl: string;
	title: TitleKey;
	followingPagePathNodes: pagePathNode[];
	previousPagePathNodes: pagePathNode[];
};

function getTitle(key: TitleKey, type: Type) {
	const langs = {
		[TitleKey.Direct]: Liferay.Language.get('direct-traffic'),
		[TitleKey.DropOffs]: Liferay.Language.get('drop-offs'),
		[TitleKey.Others]:
			type === Type.Previous
				? Liferay.Language.get('other-referrals')
				: Liferay.Language.get('other-pages')
	};

	return langs[key] || decodeURIComponent(key);
}

function getColor(key: TitleKey) {
	if (
		key === TitleKey.Direct ||
		key === TitleKey.DropOffs ||
		key === TitleKey.Others
	) {
		return SECONDARY_NODE_COLOR;
	}

	return null;
}

function formatData({pagePath}: {pagePath: pagePathNode}) {
	const formatNodes = (nodes: pagePathNode[], type: Type) =>
		nodes
			?.filter(({views}) => !!views)
			?.map(({canonicalUrl, title, views}) => ({
				color: getColor(title),
				id: uuidv4(),
				name: getTitle(title, type),
				type,
				url: decodeURIComponent(canonicalUrl),
				views
			}));

	const mainNode = {
		id: uuidv4(),
		main: true,
		name: decodeURIComponent(pagePath.title),
		url: decodeURIComponent(pagePath.canonicalUrl),
		views: pagePath.views
	};

	const previousNodes = formatNodes(
		pagePath.previousPagePathNodes,
		Type.Previous
	);

	const followingNodes = formatNodes(
		pagePath.followingPagePathNodes,
		Type.Following
	);

	const links = [...previousNodes, ...followingNodes].map((link, index) => ({
		source: link.type === Type.Previous ? index + 1 : 0,
		target: link.type === Type.Previous ? 0 : index + 1,
		value: link.views
	}));

	const nodes = [mainNode, ...previousNodes, ...followingNodes];

	return {
		links,
		nodes
	};
}

const PagePathCard = ({pathRangeSelectors, selectedSegment}) => {
	const {channelId, title, touchpoint} = useParams();
	const {data, error, loading} = useQuery(PagePathQuery, {
		variables: {
			canonicalUrl: decodeURIComponent(touchpoint),
			channelId,
			title: decodeURIComponent(title),
			...(selectedSegment?.id && {
				segmentId: selectedSegment.id
			}),
			...getSafeRangeSelectors(pathRangeSelectors)
		}
	});

	return (
		<Card minHeight={600}>
			<Card.Header>
				<Card.Title>{Liferay.Language.get('path-analysis')}</Card.Title>
			</Card.Header>
			<Card.Body className='d-flex align-items-center justify-content-center'>
				<StatesRenderer empty={!data} error={!!error} loading={loading}>
					<StatesRenderer.Loading />

					<StatesRenderer.Error apolloError={error}>
						<ErrorDisplay />
					</StatesRenderer.Error>

					{!!data && (
						<StatesRenderer.Success>
							<Sankey data={formatData(data)} />
						</StatesRenderer.Success>
					)}
				</StatesRenderer>
			</Card.Body>
		</Card>
	);
};

export default PagePathCard;
