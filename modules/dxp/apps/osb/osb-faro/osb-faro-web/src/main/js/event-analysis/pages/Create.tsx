import BaseEventAnalysisPage from '../components/BaseEventAnalysisPage';
import React from 'react';
import {AttributesProvider} from 'event-analysis/components/event-analysis-editor/context/attributes';

const Create: React.FC<React.HTMLAttributes<HTMLElement>> = () => (
	<AttributesProvider>
		<BaseEventAnalysisPage />
	</AttributesProvider>
);

export default Create;
