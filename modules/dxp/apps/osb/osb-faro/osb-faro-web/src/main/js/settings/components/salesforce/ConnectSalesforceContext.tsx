import React, {createContext, useContext, useState} from 'react';
import {DataSource} from 'shared/util/records';

interface IConnectSalesforceContext {
	dataSource: DataSource | null;
	setDataSource: (dataSource: DataSource) => void;
}

const ConnectSalesforceContext = createContext<IConnectSalesforceContext>({
	dataSource: null,
	setDataSource: () => {}
});

export const useConnectSalesforce = () => useContext(ConnectSalesforceContext);

export const ConnectSalesforceProvider = ({children}) => {
	const [dataSource, setDataSource] = useState<DataSource | null>(null);

	return (
		<ConnectSalesforceContext.Provider
			value={{
				dataSource,
				setDataSource: dataSource =>
					setDataSource(
						dataSource ? new DataSource(dataSource) : null
					)
			}}
		>
			{children}
		</ConnectSalesforceContext.Provider>
	);
};
