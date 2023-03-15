@spira_Assets @Assets @team_FARO @priority_3
Feature: Assert 180 day time filter in Documents and Media
	As an Business User, I should be able to change the time filter to 180 days in the Documents and Media page

	Background: [Setup] Navigate to Assets page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Assets" page
		* I click the "Documents and Media" tab

	Scenario: Change time filter in Documents and Media page to 180 days
		Given I click the "Last 30 days" dropdown in the card
		When I click the "More Preset Periods" button
		And I click the "Last 180 days" button
		Then I should see time filter "Last 180 days" in card