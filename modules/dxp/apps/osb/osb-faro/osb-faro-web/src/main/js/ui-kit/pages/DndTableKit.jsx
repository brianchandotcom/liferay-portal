import DndTable from 'shared/components/dnd-table';
import React, {useState} from 'react';
import Row from '../components/Row';
import {individualsListColumns} from 'shared/util/table-columns';

const DndTableKit = () => {
	const [items, setItems] = useState([
		{
			activitiesCount: 23,
			id: '379649773334099362',
			lastActivityDate: 1572879600000,
			name: 'Abram Bauch',
			properties: {
				email: 'Abram.Bauch@hotmail.com',
				jobTitle: 'Investor Metrics Representative',
				worksFor: 'Lesch, Walsh and Stracke'
			}
		},
		{
			activitiesCount: 0,
			id: '379650044967606394',
			lastActivityDate: null,
			name: 'Abram Farrell',
			properties: {
				email: 'Abram.Farrell@gmail.com',
				jobTitle: 'Direct Marketing Producer',
				worksFor: 'Jenkins, Erdman and Medhurst'
			}
		},
		{
			activitiesCount: 0,
			id: '379650011640220280',
			lastActivityDate: null,
			name: 'Adelina Kohler',
			properties: {
				email: 'Adelina.Kohler@hotmail.com',
				jobTitle: 'Corporate Tactics Representative',
				worksFor: 'Rice Group'
			}
		},
		{
			accountNames: ['Kertzmann, Kilback and Watsica'],
			activitiesCount: 8,
			draggable: false,
			id: '379649805739722238',
			lastActivityDate: 1572505200000,
			name: 'Adrienne Johnston',
			properties: {
				email: 'Adrienne.Johnston@hotmail.com',
				jobTitle: 'District Research Officer',
				worksFor: 'Kertzmann, Kilback and Watsica'
			}
		},
		{
			activitiesCount: 1,
			draggable: false,
			id: '379649828466793072',
			lastActivityDate: 1572656400000,
			name: 'Alana Nicolas',
			properties: {
				email: 'Alana.Nicolas@hotmail.com',
				jobTitle: 'Corporate Security Executive',
				worksFor: 'Schowalter-Wisozk'
			}
		}
	]);

	return (
		<div>
			<Row>
				<DndTable
					columns={[
						individualsListColumns.getNameEmail({groupId: '32717'}),
						individualsListColumns.jobTitle,
						individualsListColumns.activitiesCount,
						{
							accessor: 'properties.worksFor',
							label: 'Works For'
						},
						individualsListColumns.getLastActivityDate('UTC')
					]}
					items={items}
					onItemsChange={setItems}
				/>
			</Row>
		</div>
	);
};

export default DndTableKit;
