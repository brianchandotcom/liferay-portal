import React from 'react';
import ClayLayout from '@clayui/layout';

interface DetailViewContentRowProps {
    title?: string;
    body: React.ReactNode;
}

export function DetailViewContentRow({title, body}: DetailViewContentRowProps): JSX.Element {
    return (
    <div className='sheet-text'>
        {title && <ClayLayout.ContentCol className='text-body'>
            <strong>{title}</strong> 
        </ClayLayout.ContentCol>}
        <ClayLayout.ContentCol>
            {body}
        </ClayLayout.ContentCol>
    </div>
    );
}