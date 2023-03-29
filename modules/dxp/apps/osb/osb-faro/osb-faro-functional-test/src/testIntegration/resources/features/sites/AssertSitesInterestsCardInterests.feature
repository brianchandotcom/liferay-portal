@spira_Sites @Sites @team_FARO @priority_3
Feature: Assert Sites Interests card highest interests
	As a Business User, I should be able to view Interests card highest interest in Sites

	Background: [Setup] Navigate to Sites page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page

	Scenario: Assert Interests card highest interests
		Given I go to the "Sites" page
		Then I should see top result "visionary platforms" in the "Interests" card