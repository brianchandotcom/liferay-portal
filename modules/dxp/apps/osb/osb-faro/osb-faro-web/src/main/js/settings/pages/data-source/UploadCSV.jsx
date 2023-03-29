import * as breadcrumbs from 'shared/util/breadcrumbs';
import autobind from 'autobind-decorator';
import BasePage from 'settings/components/BasePage';
import FileDropTarget from 'shared/components/FileDropTarget';
import Form, {
	toPromise,
	validateMaxLength,
	validateRequired
} from 'shared/components/form';
import FormNavigation from 'settings/components/FormNavigation';
import getCN from 'classnames';
import NavigationWarning from 'shared/components/NavigationWarning';
import React from 'react';
import Sheet from 'shared/components/Sheet';
import {PropTypes} from 'prop-types';
import {Routes, setUriQueryValue, toRoute} from 'shared/util/router';
import {sequence} from 'shared/util/promise';
import {validateUniqueName} from 'shared/util/data-sources';
import {withAdminPermission} from 'shared/hoc';

@withAdminPermission
export default class UploadCSV extends React.Component {
	static propTypes = {
		groupId: PropTypes.string.isRequired,
		history: PropTypes.object.isRequired
	};

	state = {
		editing: true,
		fileUploaded: false,
		fileVersionId: null
	};

	constructor(props) {
		super(props);

		this._cachedNameValues = new Map();

		this._formRef = React.createRef();
	}

	getNextButtonUrl() {
		const {
			props: {groupId},
			state: {fileVersionId}
		} = this;

		const {name} = this._formRef.current.getFormikBag().values;

		if (fileVersionId) {
			const url = toRoute(Routes.SETTINGS_CSV_UPLOAD_CONFIGURE, {
				fileVersionId: String(fileVersionId),
				groupId
			});

			return name ? setUriQueryValue(url, 'name', name) : url;
		}

		return '';
	}

	@autobind
	handleFileChange(file) {
		if (file) {
			const {completed, response, status} = file;

			this.setState({
				fileUploaded: completed && status !== 500,
				fileVersionId: Number(response)
			});
		}
	}

	@autobind
	handleNextClick() {
		const {history} = this.props;

		this.setState({editing: false}, () => {
			history.push(this.getNextButtonUrl());
		});
	}

	@autobind
	handleValidate(value) {
		const {groupId} = this.props;

		let error = '';

		if (this._cachedNameValues.has(value)) {
			error = this._cachedNameValues.get(value);
		} else {
			error = validateUniqueName({groupId, value});

			this._cachedNameValues.set(value, error);
		}

		return toPromise(error);
	}

	render() {
		const {
			props: {className, groupId},
			state: {editing, fileUploaded}
		} = this;

		return (
			<BasePage
				breadcrumbItems={[
					breadcrumbs.getDataSources({groupId}),
					{
						href: toRoute(Routes.SETTINGS_ADD_DATA_SOURCE, {
							groupId
						}),
						label: Liferay.Language.get('add-data-source')
					},
					{
						active: true,
						label: Liferay.Language.get('new-csv')
					}
				]}
				className={getCN('upload-csv-root', className)}
				documentTitle={Liferay.Language.get('csv-file')}
				groupId={groupId}
			>
				<Sheet>
					<Sheet.Header>
						<div className='header-content'>
							<h3 className='title'>
								{Liferay.Language.get('csv-file')}
							</h3>
						</div>
					</Sheet.Header>

					<Form initialValues={{name: ''}} ref={this._formRef}>
						{({isValid, values: {name}}) => (
							<Form.Form>
								<NavigationWarning
									when={(!!name || fileUploaded) && editing}
								/>

								<Sheet.Body>
									<Form.Group autoFit>
										<Form.Input
											label={Liferay.Language.get(
												'name-data-source'
											)}
											name='name'
											required
											validate={sequence([
												validateRequired,
												validateMaxLength(255),
												this.handleValidate
											])}
											width={30}
										/>
									</Form.Group>

									<Form.Group className='upload-content'>
										<Form.Label>
											{Liferay.Language.get(
												'file-upload'
											)}
										</Form.Label>

										<FileDropTarget
											fileTypes={['.csv', '.txt']}
											name={Liferay.Language.get(
												'csv-file'
											)}
											onChange={this.handleFileChange}
											uploadURL={`/o/faro/contacts/${groupId}/data_source/upload`}
											useJaxRS
										/>
									</Form.Group>
								</Sheet.Body>

								<Sheet.Footer divider={false}>
									<FormNavigation
										cancelHref={toRoute(
											Routes.SETTINGS_ADD_DATA_SOURCE,
											{
												groupId
											}
										)}
										enableNext={isValid && fileUploaded}
										onNextStep={this.handleNextClick}
										submitMessage={Liferay.Language.get(
											'next'
										)}
									/>
								</Sheet.Footer>
							</Form.Form>
						)}
					</Form>
				</Sheet>
			</BasePage>
		);
	}
}
