@spira_Interest_Keywords_Blocklist @Keywords @Interest_Keywords_Blocklist @team_FARO @priority_4
Feature: Delete a keyword block
	As a Business User, I should be able to delete a keyword block

	Background: [Setup] Navigate to Interests Topic
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Keywords" page

	Scenario: Delete a keyword block
		Given I click the "Add Keyword" button
		And I name the keyword "block"
		And I click the "Send" button
		When I delete the keyword in row "1"
		And I click the "Continue" button
		Then I should see text saying "You have not added keywords to the blocklist yet." on the page