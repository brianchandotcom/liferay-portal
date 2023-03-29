@spira_Sites @Sites @team_FARO @priority_3
Feature: Assert Sites Acquisition card Channels highest session
	As a Business User, I should be able to view Channels highest session in Sites

	Background: [Setup] Navigate to Sites page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page

	Scenario: Assert Acquisition Channels highest session
		Given I go to the "Sites" page
		Then I should see the channel named "direct" in the table