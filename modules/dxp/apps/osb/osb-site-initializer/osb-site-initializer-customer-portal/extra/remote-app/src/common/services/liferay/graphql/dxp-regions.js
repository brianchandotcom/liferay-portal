
const getDXPRegions = () => {
  return {
    query: `{
          c {
            dXPCDataCenterRegions {
              items {
                dxpcDataCenterRegionId,
                name
              }
            }
          }
        }`
  };
};

export { getDXPRegions };