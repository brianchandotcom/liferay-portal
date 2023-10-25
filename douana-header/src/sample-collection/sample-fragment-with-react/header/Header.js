import React from "react";

import UpperSide from "./components/upper-side/UpperSide";
import LowerSide from "./components/lower-side/LowerSide";

export default function Header() {
  return (
    <div className="container-fluid">
      <div className="row">
        <div className="col-4" id="logo">
          <img src={"documents/20118/33080/logo.png"} alt="logo" />
        </div>
        <div className="col-8">
          <div className="container-fluid">
            <div className="row d-flex align-items-center justify-content-center">
              <div className="col-9">
                <UpperSide></UpperSide>
              </div>
            </div>
            <div className="row">
              <div className="col-12">
                <LowerSide></LowerSide>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
