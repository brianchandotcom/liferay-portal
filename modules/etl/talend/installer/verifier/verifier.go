package verifier

import (
	"fmt"
	"log"
	"path/filepath"
	"strings"

	"installer/constants"
	"installer/utils/fileutils"

	"github.com/go-ini/ini"
)

func VerifyInstallerIntegrity() {
	if !fileutils.Exists(constants.ComponentArtifactsDir) {
		log.Println(
			"Liferay component artifacts are missing from the installer: " +
				constants.ComponentArtifactsDir)
		log.Fatal("Installation failed")
	}

	if !fileutils.Exists(constants.ComponentDefinitionDir) {
		log.Println(
			"Liferay component definition is missing from the installer: " +
				constants.ComponentDefinitionDir)
		log.Fatal("Installation failed")
	}

	if !fileutils.Exists(constants.ThirdPartyDependenciesDir) {
		log.Println(
			"3rd party dependencies are missing from the installer: " +
				constants.ThirdPartyDependenciesDir)
		log.Fatal("Installation failed")
	}
}

func VerifyTOSLocation() {
	if !fileutils.Exists(constants.TosHomeDir) {
		log.Println("TOS Home directory does not exist")
		log.Fatal("Installation failed")
	}
	if !fileutils.Exists(constants.TosM2Dir) {
		log.Println(
			"TOS Maven local repository's directory does not exist. Please " +
				"make sure that the Talend Open Studio has been already " +
					"started at least once before running the installer.")
		log.Fatal("Installation failed")
	}

	fmt.Println("Talend Open Studio's Maven repository: \t" +
		constants.TosM2Dir)
}

func VerifyTOSVersion() {
	tosConfigIniPath := filepath.Join(
		constants.TosHomeDir, "configuration", "config.ini")
	cfg, err := ini.Load(tosConfigIniPath)

	if err != nil {
		log.Printf("Fail to read TOS's config file: %v \n", err)
		log.Fatal("Installation failed")
	}

	tosVersion := cfg.Section("").Key("talend.studio.version").String()

	if !strings.HasPrefix(tosVersion, constants.SUPPORTED_TOS_VERSION) {
		log.Println("Unsupported Talend Version: " + tosVersion)
		log.Println("The installer is only compatible with " +
			constants.SUPPORTED_TOS_VERSION)
		log.Fatal("Installation failed")
	}
}
