import React, {createContext, useContext, useState} from 'react';
import {DataSource} from 'shared/util/records';

interface IWizardPageContext {
	dataSource: DataSource | null;
	setDataSource: (dataSource: DataSource) => void;
}

const WizardPageContext = createContext<IWizardPageContext>({
	dataSource: null,
	setDataSource: () => {}
});

export const useWizardPage = () => useContext(WizardPageContext);

export const WizardPageProvider = ({children}) => {
	const [dataSource, setDataSource] = useState<DataSource | null>(null);

	return (
		<WizardPageContext.Provider
			value={{
				dataSource,
				setDataSource: dataSource =>
					setDataSource(
						dataSource ? new DataSource(dataSource) : null
					)
			}}
		>
			{children}
		</WizardPageContext.Provider>
	);
};
