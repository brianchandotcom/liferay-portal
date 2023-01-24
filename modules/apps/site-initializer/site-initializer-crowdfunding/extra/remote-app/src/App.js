import React, { useEffect, useState } from 'react';
import Dropdown from './components/Dropdown';
import ProductInfo from './components/ProductInfo';
import Checkbox from './components/Checkbox';
import { getSpecificationsProduct, getProduct, getSkuProduct } from './service/Product';
import { createOrder, checkoutOrder, insertOrderObject } from './service/Order';
import getParams from './utils/GetParams';

function App() {
  const [product, setProduct] = useState();
  const [accountId, setAccountId] = useState();
  const [investmentAmount, setInvestmentAmount] = useState();
  const [channelId, setChannelId] = useState();
  const [productId, setProductId] = useState();
  const [skuId, setSkuId] = useState(0);
  const [productSpecifications, setProductSpecifications] = useState();
  const [termsOfAgreement, setTermsOfAgreement] = useState(false);
  const [orderStatus, setOrderStatus] = useState("Open");

  useEffect(() => {
    const { productIdParam, accountIdParam, channelIdParam, investmentParam } = getParams();
    setProductId(productIdParam);
    setAccountId(accountIdParam);
    setChannelId(channelIdParam);
    setInvestmentAmount(investmentParam);

    if (productIdParam) {
      getProduct(productIdParam)
        .then(product => {
          setProduct(product);
          return getSpecificationsProduct(productIdParam);
        })
        .then(productSpecifications => {
          setProductSpecifications(productSpecifications);
          return getSkuProduct(productIdParam);
        })
        .then(skus => setSkuId(skus.items[0].id))
        .catch(err => console.error(err));
    }
  }, []);

  const sendOrder = () => {
    createOrder(channelId, accountId, productId, skuId).then(order => {
      return checkoutOrder(order.id);
    })
      .then(order => {
        setOrderStatus(order.orderStatusInfo.label)
        return insertOrderObject(order.id, productId, investmentAmount);
      }).then(() => console.log("success"));
  }

  const handleChangeTermsOfAgreement = () => {
    setTermsOfAgreement(!termsOfAgreement);
  };

  return (
    <div className="crowse-container">
      <div className="container">
        <Dropdown title="Offer Submission & Confirmation" subtitle="You submit an offer for review and pre-approval." index={1}>
          <div className="row">
            <div className="col-sm-12 col-md-6">
              <label className="input-label">Investment Commitment *</label>
              <input type="text" value={investmentAmount} onChange={(e) => setInvestmentAmount(e.target.value)} />
              <span className="input-legend">$100,000 minimum investment commitment. <br />100% of commitment required to close.</span>
            </div>
            <div className="col-sm-12 col-md-6">
              <label className="input-label">Phone number *</label>
              <input type="text" />
              <span className="input-legend">All investors will be contacted.</span>
            </div>
            <div className="col-sm-12 mt-4">
              <h1 className="disclosure-title">Overview Disclosures/Disclaimers</h1>
              <p className="disclosure-description">This overview does not constitute an offer to sell you a security, or a solicitation of an offer by you to purchase a security. No offer to sell or solicitation of an offer to buy may be made prior to the delivery by the project sponsor of definitive documentation relating to a proposed investment (collectively, the "Offering Materials"), including, in most cases, (1) an offering circular or private placement</p>
            </div>
            <div className="col-sm-12 mt-4">
              <Checkbox checked={termsOfAgreement} onChange={handleChangeTermsOfAgreement} label="I have read the foregoing explanation and disclaimer and understand its contents, including that I will be asked to review, complete, execute and deliver additional materials, including a subscription or similar agreement, before my commitment to invest in a project will be complete, and no subscription will be binding until it is accepted by a project sponsor in such sponsor's sole discretion." />
            </div>
            <div className="col-sm-12 mt-4">
              <button className={!termsOfAgreement && 'disabled'} onClick={sendOrder} disabled={!termsOfAgreement}>Submit Offer</button>
            </div>
          </div>
        </Dropdown>
        <Dropdown title="Upload Documents" subtitle="You review, sign, and deliver closing documents, which the Sponsor reviews and pre-approves." index={2} />
        <Dropdown title="Investor Verification" subtitle='You provide verification that you are an "accredited investor" as mandated by SEC guidelines.' index={3} />
        <Dropdown title="Funding" subtitle="You receive funding instructions and provide funds." index={4} />
      </div>
      <aside>
        <ProductInfo product={product} specifications={productSpecifications} status={orderStatus} />
      </aside>
    </div>
  );
}



export default App;
