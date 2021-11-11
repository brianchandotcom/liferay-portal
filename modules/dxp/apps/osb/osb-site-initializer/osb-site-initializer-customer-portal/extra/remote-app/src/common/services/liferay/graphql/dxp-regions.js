const getDXPRegions = () => {
	return `c {
            dXPCDataCenterRegions {
              items {
                dxpcDataCenterRegionId,
                name
              }
            }
          }`;
};

export {getDXPRegions};
