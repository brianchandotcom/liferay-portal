import Modal from 'shared/components/modal';
import React from 'react';
import Table from 'shared/components/table';

interface IIndividualAttributesModalInterface
	extends React.HTMLAttributes<HTMLElement> {
	dataSources: {
		dataSourceFieldName: string;
		dataSourceName: string;
	}[];
	fieldName: string;
	onClose: () => void;
}

const IndividualAttributesModal: React.FC<IIndividualAttributesModalInterface> = ({
	dataSources,
	fieldName,
	onClose
}) => (
	<Modal>
		<Modal.Header onClose={onClose} title={fieldName} />

		<Modal.Body>
			<h5>{Liferay.Language.get('data-sources')}</h5>

			<Table
				columns={[
					{
						accessor: 'dataSourceName',
						label: Liferay.Language.get('source'),
						sortable: false
					},
					{
						accessor: 'dataSourceFieldName',
						label: Liferay.Language.get('attribute[noun]'),
						sortable: false
					}
				]}
				items={dataSources}
				rowIdentifier='dataSourceName'
			/>
		</Modal.Body>
	</Modal>
);

export default IndividualAttributesModal;
