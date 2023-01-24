import React from 'react';

const ProductInfo = ({ product, specifications, status }) => {
    const specificationsList = [];

    if (specifications) {
        specifications.items.forEach(specification => {
            specificationsList[specification.specificationKey] = specification.value["en_US"];
        });
    }

    return (
        <>
        { product &&
        <>
            <div className="crowse-product-info">
                <h1>Offer Summery</h1>
                <div className="thumbnail" style={{ backgroundImage: `url(${product.thumbnail})` }}></div>
                <span className="location">{specificationsList["location"]}</span>
                <h1>{product.name["en_US"]}</h1>
                <hr />
                <div className="d-flex justify-content-between">
                    <span>Sponsor Historic Net IRR</span>
                    <span>{specificationsList["sponsor-historic-net-irr"]}</span>
                </div>
                <div className="d-flex justify-content-between">
                    <span>Sponsor Historic Equity Multiple</span>
                    <span>{specificationsList["sponsor-historic-equity-multiple"]}</span>
                </div>
                <div className="d-flex justify-content-between">
                    <span>Investment Profile</span>
                    <span>{specificationsList["investment-profile"]}</span>
                </div>
                <hr />
                <div className="d-flex justify-content-between">
                    <span>Offer Status</span>
                    <span className={`status ${status.toLowerCase()}`}>{status}</span>
                </div>
                <hr />
                <span className="d-block">For assistance</span>
                <span className="d-block">call <b>(888) 432-7693</b></span>
                <span className="d-block">email <b>help@crowse.com</b></span>

            </div>
        </>
        }
        </> 
    )
}

export default ProductInfo