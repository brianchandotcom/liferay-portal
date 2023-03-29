import {safeResultToProps} from 'shared/util/mappers';

const mapResultToProps: object = safeResultToProps(({dataSources}) => ({
	sites: dataSources
}));

export {mapResultToProps};
