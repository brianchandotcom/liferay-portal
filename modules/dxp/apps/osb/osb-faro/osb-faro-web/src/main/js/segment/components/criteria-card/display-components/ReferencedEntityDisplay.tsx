import React, {useContext} from 'react';
import {
	EntityType,
	ReferencedObjectsContext
} from 'segment/segment-editor/dynamic/context/referencedObjects';
import {parseReferencedEntityId} from 'segment/segment-editor/dynamic/utils/utils';
import {sub} from 'shared/util/lang';

const ReferencedEntity: React.FC<{
	id: string;
	label: string;
	type: EntityType;
}> = ({id, label, type}) => {
	const {referencedEntities} = useContext(ReferencedObjectsContext);

	const entity = referencedEntities.getIn([
		type,
		parseReferencedEntityId(id, referencedEntities, type)
	]);

	return entity ? (
		<b>{`'${entity.get('name')}'`}</b>
	) : (
		<b className='undefined-entity'>
			{sub(Liferay.Language.get('undefined-x'), [label])}
		</b>
	);
};

export default ReferencedEntity;
