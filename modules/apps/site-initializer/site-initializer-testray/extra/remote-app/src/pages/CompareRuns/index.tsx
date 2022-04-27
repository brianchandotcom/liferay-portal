/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {Link} from 'react-router-dom';

import Container from '../../components/Layout/Container';
import QATable from '../../components/Table/QATable';
import TableChart from '../../components/TableChart';
import useTableChartData from '../../data/useTableChartData';
import i18n from '../../i18n';

const CompareRuns: React.FC = () => {
	const {colors, columns, data} = useTableChartData();

	return (
		<Container collapsable title={i18n.translate('details')}>
			<div className="d-flex flex-wrap">
				<div className="col-8 col-lg-8 col-md-12">
					<QATable
						items={[
							{
								title: `${i18n.translate('run')} A`,
								value: <Link to="">1660207710</Link>,
							},
							{
								title: i18n.translate('project-name'),
								value: <Link to="">Liferay Portal 7.4</Link>,
							},
							{
								title: i18n.translate('build'),
								value: (
									<Link to="">
										[master] ci:test:upstream-dxp - 700 -
										2022-04-09[18:16:18]
									</Link>
								),
							},
							{
								divider: true,
								title: i18n.translate('environment'),
								value:
									'Tomcat 9.0 + Chrome 86.0 + IBM DB2 11.1 + Oracle JDK 8 64-Bit + CentOS 7 64-Bit',
							},
							{
								title: `${i18n.translate('run')} A`,
								value: <Link to="">1660253173</Link>,
							},
							{
								title: i18n.translate('project-name'),
								value: <Link to="">Liferay Portal 7.4</Link>,
							},
							{
								title: i18n.translate('build'),
								value: (
									<Link to="">
										[master] ci:test:upstream-dxp - 700 -
										2022-04-09[18:16:18]
									</Link>
								),
							},
							{
								title: i18n.translate('environment'),
								value:
									'Tomcat 9.0 + Chrome 86.0 + IBM DB2 11.1 + Oracle JDK 8 64-Bit + CentOS 7 64-Bit',
							},
						]}
					/>
				</div>

				<div className="col-4 col-lg-4 col-md-12 pb-5">
					<TableChart
						colors={colors}
						columns={columns}
						data={data}
						title="Number of Case Results"
					/>
				</div>
			</div>
		</Container>
	);
};

export default CompareRuns;
