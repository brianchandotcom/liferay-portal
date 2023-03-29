import Body from './Body';
import Context from './Context';
import DocumentTitle from 'shared/components/DocumentTitle';
import getCN from 'classnames';
import Header from './Header';
import MaintenanceAlert from 'shared/components/MaintenanceAlert';
import React from 'react';
import Row from './Row';
import SubHeader from './SubHeader';

interface IBasePageProps extends React.HTMLAttributes<HTMLElement> {
	documentTitle: string;
}

const BasePage: React.FC<IBasePageProps> & {
	Body: typeof Body;
	Context: typeof Context;
	Header: typeof Header;
	Row: typeof Row;
	SubHeader: typeof SubHeader;
} = ({children, className, documentTitle}) => (
	<div className={getCN('index-root', className)}>
		<DocumentTitle title={documentTitle} />

		<MaintenanceAlert stripe />

		{children}
	</div>
);

BasePage.Body = Body;
BasePage.Context = Context;
BasePage.Header = Header;
BasePage.Row = Row;
BasePage.SubHeader = SubHeader;

export default BasePage;
