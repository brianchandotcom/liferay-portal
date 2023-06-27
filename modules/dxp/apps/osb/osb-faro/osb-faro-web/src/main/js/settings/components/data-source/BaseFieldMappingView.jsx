import * as API from 'shared/api';
import autobind from 'autobind-decorator';
import BaseDataSourcePage from './BasePage';
import DataTransformationList from 'settings/components/data-transformation-list';
import ErrorDisplay from 'shared/components/ErrorDisplay';
import Loading from 'shared/components/Loading';
import omitDefinedProps from 'shared/util/omitDefinedProps';
import React from 'react';
import Sheet from 'shared/components/Sheet';
import {autoCancel, hasRequest} from 'shared/util/request-decorator';
import {DataSource, User} from 'shared/util/records';
import {List, Map} from 'immutable';
import {PropTypes} from 'prop-types';

@hasRequest
export default class BaseFieldMappingView extends React.Component {
	static defaultProps = {
		pageTitle: Liferay.Language.get('configure-data-source')
	};

	static propTypes = {
		context: PropTypes.string.isRequired,
		currentUser: PropTypes.instanceOf(User),
		dataSource: PropTypes.instanceOf(DataSource),
		details: PropTypes.any,
		groupId: PropTypes.string.isRequired,
		id: PropTypes.string,
		pageTitle: PropTypes.string,
		title: PropTypes.any
	};

	state = {
		error: false,
		fieldsIList: new List(),
		loading: true,
		mappingSuggestions: {},
		sourceFields: {}
	};

	componentDidMount() {
		this.handleFetchMappings();
	}

	@autoCancel
	@autobind
	handleFetchMappings() {
		const {context, groupId, id} = this.props;

		this.setState({
			loading: true
		});

		return API.dataSource
			.fetchMappingsLite({context, groupId, id})
			.then(mappings => {
				const mappingSuggestions = {};
				const sourceFields = {};

				for (const {name, suggestions, values} of mappings) {
					mappingSuggestions[name] = suggestions;
					sourceFields[name] = values[0];
				}

				this.setState({
					error: false,
					fieldsIList: this.processMappings(mappings),
					loading: false,
					mappingSuggestions,
					sourceFields
				});
			})
			.catch(err => {
				if (!err.IS_CANCELLATION_ERROR) {
					this.setState({
						error: true,
						loading: false
					});
				}
			});
	}

	processMappings(mappings) {
		const {id} = this.props;

		if (id && mappings.find(({mapping}) => mapping)) {
			mappings = mappings.filter(({mapping}) => mapping);
		}

		return new List(
			mappings.map(({mapping, name, suggestions, values}) => {
				const suggestion = id ? mapping : suggestions[0];

				return new Map({
					source: new Map({
						name,
						value: values[0]
					}),
					suggestion: new Map({
						name: suggestion && suggestion.name,
						value: suggestion && suggestion.values[0]
					})
				});
			})
		);
	}

	renderList() {
		const {
			props: {groupId, id},
			state: {
				error,
				fieldsIList,
				loading,
				mappingSuggestions,
				sourceFields
			}
		} = this;

		if (loading) {
			return <Loading key='LOADING' />;
		} else if (error) {
			return (
				<ErrorDisplay
					key='ERROR_DISPLAY'
					onReload={this.handleFetchMappings}
					spacer
				/>
			);
		} else {
			return (
				<DataTransformationList
					fieldsIList={fieldsIList}
					groupId={groupId}
					id={id}
					key='DATA_TRANSFORMATION_LIST'
					mappingSuggestions={mappingSuggestions}
					readOnly
					sourceFields={sourceFields}
					sourceTitle={Liferay.Language.get('salesforce-field')}
					suggestionsTitle={Liferay.Language.get(
						'analytics-cloud-field'
					)}
				/>
			);
		}
	}

	render() {
		const {
			currentUser,
			dataSource,
			details,
			groupId,
			id,
			pageTitle,
			title,
			...otherProps
		} = this.props;

		return (
			<BaseDataSourcePage
				{...omitDefinedProps(
					otherProps,
					BaseFieldMappingView.propTypes
				)}
				className='base-field-mapping-view-root'
				currentUser={currentUser}
				dataSource={dataSource}
				groupId={groupId}
				id={id}
				pageTitle={pageTitle}
			>
				<Sheet>
					<Sheet.Header>
						<Sheet.Section>
							<h4>{title}</h4>

							<Sheet.Text>{details}</Sheet.Text>
						</Sheet.Section>
					</Sheet.Header>

					{this.renderList()}
				</Sheet>
			</BaseDataSourcePage>
		);
	}
}
