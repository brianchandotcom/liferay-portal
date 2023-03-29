import {getVariables, safeResultToProps} from 'shared/util/mappers';

const assetTypeLabels = {
	blog: Liferay.Language.get('blogs'),
	custom: Liferay.Language.get('custom'),
	document: Liferay.Language.get('documents-and-media'),
	form: Liferay.Language.get('forms'),
	journal: Liferay.Language.get('web-content')
};

const mapResultToProps = safeResultToProps(({assets}) => {
	const items = assets.map(
		({assetId, assetTitle, assetType, defaultMetric}) => ({
			assetId,
			assetType,
			interactions: defaultMetric.value,
			title: assetTitle || assetId,
			type: assetTypeLabels[assetType]
		})
	);

	return {items};
});

/**
 * Map Props to Options
 * @param {object} param0 props
 * @param {object} param1 context
 */
const mapPropsToOptions = ({
	filters,
	rangeSelectors,
	router: {params},
	touchpoint
}) => {
	const {variables} = getVariables({filters, params, rangeSelectors});

	if (touchpoint) {
		return {
			variables: {
				...variables,
				touchpoint: decodeURIComponent(touchpoint)
			}
		};
	}

	return getVariables({
		filters,
		params,
		rangeSelectors
	});
};

export {mapPropsToOptions, mapResultToProps};
