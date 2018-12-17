package constants

import (
	"flag"
	"log"
	"os"
	"path/filepath"
)

const (
	SUPPORTED_TOS_VERSION = "7.1.1"

	componentArtifactsPath = "component-artifacts"
	componentDefinitionPath = "component-definition"
	thirdPartyDependenciesPath = "3rd-party-dependencies"
)

var (
	tosMavenM2PathSegments = [...]string{"configuration", ".m2"}

	ComponentArtifactsDir = filepath.Join(
		GetBinaryLocation(), componentArtifactsPath)
	ComponentDefinitionDir = filepath.Join(
		GetBinaryLocation(), componentDefinitionPath)
	ThirdPartyDependenciesDir = filepath.Join(
		GetBinaryLocation(), thirdPartyDependenciesPath)

	TosHomeDir = getTosHomeDirectory()
	TosM2HomeDir = filepath.Join(TosHomeDir, tosMavenM2PathSegments[0])
	TosM2Dir = filepath.Join(
		TosHomeDir, tosMavenM2PathSegments[0], tosMavenM2PathSegments[1])
)

func GetBinaryLocation() string {
	dir, err := filepath.Abs(filepath.Dir(os.Args[0]))
	if err != nil {
		log.Fatal(err)
	}

	return dir
}

func getTosHomeDirectory() string {
	tosPtr := flag.String("tos", "", "Talend Open Studio's home directory")
	flag.Parse()

	tos := *tosPtr

	if tos == "" {
		log.Println("Talend Open Studio's Home folder is not specified " +
			"with \"-tos\" flag")
		log.Println("Usage is: \"" + filepath.Base(os.Args[0]) +
			" -tos=PATH_TO_THE_TOS_HOME_DIRECTORY\"\n")
		log.Fatal("Installation failed")
	}

	return tos
}
