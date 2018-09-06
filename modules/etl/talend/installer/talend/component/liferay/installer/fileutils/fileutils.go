package fileutils

import (
	"io/ioutil"
	"log"
	"path/filepath"

	"github.com/ZoltanTakacs/golang/talend/component/liferay/installer/constants"
)

func GetSrcComponentDefinitionName() string {
	files, err := ioutil.ReadDir(constants.ComponentDefinitionDir)
	if err != nil || len(files) == 0 {
		log.Fatalf("Unable to read the component definition file: %v", err)
	}

	return files[0].Name()
}

// Get the definition file path from the installer's dependencies
func GetSrcComponentDefinitionPath() string {
	return filepath.Join(constants.ComponentDefinitionDir, GetSrcComponentDefinitionName())
}
