import React from 'react';

import PictureBox from './PictureBox.es';
import DetailsBox from './DetailsBox.es';


function AreaViewer() {
    return (
        <div className="row">
            <div className="col">
                <PictureBox />
            </div>
            <div className="col col-sm-4">
                <DetailsBox />
            </div>
        </div>
    );
}

export default AreaViewer;
