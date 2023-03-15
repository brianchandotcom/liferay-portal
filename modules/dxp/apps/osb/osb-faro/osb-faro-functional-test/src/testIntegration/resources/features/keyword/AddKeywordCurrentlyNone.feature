@spira_Interest_Keywords_Blocklist @Keywords @Interest_Keywords_Blocklist @team_FARO @priority_4
Feature: Add keyword when there are none
	As a Business User, I should be able to add a keyword when there are none

	Background: [Setup] Navigate to Interest Topics
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Keywords" page

	Scenario: Add keyword to blocklist
		Given I should see text saying "You have not added keywords to the blocklist yet." on the page
		When I click the "Add Keyword" button
		And I name the keyword "block"
		And I click the "Send" button
		Then I should see the keyword named "block" in the table